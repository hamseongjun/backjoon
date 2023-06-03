package database;

import java.util.*;

class TableImpl implements Table {
    private final String name;
    private final List<Column> columns;

    TableImpl(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
    }

    TableImpl(Table table) {
        this.name = table.getName();
        this.columns = new ArrayList<>();
        for (int i = 0; i < table.getColumnCount(); i++) {
            Column column = table.getColumn(i);
            this.columns.add(column);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void show() {
        List<Integer> widths = new ArrayList<>();
        for (Column column : columns) {
            widths.add(getColumnWidth(column));
            int i = columns.indexOf(column);
            System.out.printf("%" + widths.get(i) + "s | ", column.getHeader());
        }
        System.out.println();
        for (int i = 0; i < getRowCount(); i++) {
            for (Column column : columns) {
                String value = column.getValue(i);
                System.out.printf("%" + widths.get(columns.indexOf(column)) + "s | ", value);
            }
            System.out.println();
        }
    }

    private int getColumnWidth(Column column) {
        int maxLength = column.getHeader().length();
        for (int i = 0; i < getRowCount(); i++) {
            int length;
            if (column.getValue(i) == null) { length = 0; }
            else { length = column.getValue(i).length(); }
            if(maxLength < length) { maxLength = length; }
        }
        return maxLength;
    }

    @Override
    public void describe() {
        System.out.println("<" + toString() + ">");
        int entries = getRowCount();
        System.out.println("RangeIndex: " + entries + " entries, 0 to " + (entries-1));
        System.out.println("Data Columns (total " + columns.size() + " columns):");

        String[] header = {"#", "Column", "Non-Null Count", "Dtype"};
        int[] maxSize = {header[0].length(), header[1].length(), header[2].length()};
        List<Object[]> rows = new ArrayList<>();

        int intType = 0, stringType = 0;
        for (int i = 0; i < getColumnCount(); i++) {
            Column column = getColumn(i);
            String columnName = column.getHeader();
            long nonNullCount = column.getNullCount();
            String dtype;
            if (column.isNumericColumn()) { dtype =  "int"; intType++;}
            else { dtype = "String"; stringType++;}

            Object[] values = {i, columnName, nonNullCount, dtype};
            rows.add(values);

            if (Integer.toString(i).length() > maxSize[0]) { maxSize[0] = Integer.toString(i).length(); }
            if (columnName.length() > maxSize[1]) { maxSize[1] = columnName.length(); }
            if (Long.toString(nonNullCount).length()+9 > maxSize[2]) { maxSize[2] = Long.toString(nonNullCount).length(); }
        }
        String s = "%" + maxSize[0] + "s | %" + maxSize[1] + "s | %" + (maxSize[2]-9) + "s";
        System.out.printf(s + " | %s\n", "#", "Column", "Non-Null Count", "Dtype");
        for (Object[] row : rows) {
            System.out.printf(s + " non-null | %s\n", row[0], row[1], row[2], row[3]);
        }
        System.out.println("dtypes: int(" + intType + "), String(" + stringType + ")");
    }

    @Override
    public Table head() {
        return head(5);
    }

    @Override
    public Table head(int lineCount) {
        List<Column> newColumns = new ArrayList<>();
        for (Column column : columns) {
            int min = Math.min(lineCount, getRowCount());
            Object[] values = new Object[min];
            for (int i = 0; i < min; i++) {
                values[i] = column.getValue(i);
            }
            newColumns.add(new ColumnImpl(column.getHeader(), values));
        }
        return new TableImpl(name, newColumns);
    }

    @Override
    public Table tail() {
        return tail(5);
    }

    @Override
    public Table tail(int lineCount) {
        List<Column> newColumns = new ArrayList<>();
        for (Column column : columns) {
            int startIndex = Math.max(0, getRowCount() - lineCount);
            int min = Math.min(lineCount, getRowCount());
            Object[] values = new Object[min];
            for (int i = 0; i < min; i++) {
                values[i] = column.getValue(i+startIndex);
            }
            newColumns.add(new ColumnImpl(column.getHeader(), values));
        }
        return new TableImpl(name, newColumns);
    }

    @Override
    public Table selectRows(int beginIndex, int endIndex) {
        List<Column> newColumns = new ArrayList<>();
        for (Column column : columns) {
            Object[] values = new Object[endIndex-beginIndex];
            for (int i = 0; i < endIndex-beginIndex; i++) {
                values[i] = column.getValue(i+beginIndex);
            }
            newColumns.add(new ColumnImpl(column.getHeader(), values));
        }
        return new TableImpl(name, newColumns);
    }

    @Override
    public Table selectRowsAt(int ...indices) {
        List<Column> newColumns = new ArrayList<>();
        for (Column column : columns) {
            Object[] values = new Object[indices.length];
            int i = 0;
            for (int index : indices) {
                values[i] = column.getValue(index);
                i++;
            }
            newColumns.add(new ColumnImpl(column.getHeader(), values));
        }
        return new TableImpl(name, newColumns);
    }

    @Override
    public Table selectColumns(int beginIndex, int endIndex) {
        List<Column> newColumns = columns.subList(beginIndex, endIndex);
        return new TableImpl(name, newColumns);
    }

    @Override
    public Table selectColumnsAt(int ...indices) {
        List<Column> newColumns = new ArrayList<>();
        for (int index : indices) {
            newColumns.add(columns.get(index));
        }
        return new TableImpl(name, newColumns);
    }

//    @Override
//    public <T> Table selectRowsBy(String columnName, Predicate<T> predicate) {}

    @Override
    public Table sort(int byIndexOfColumn, boolean isAscending, boolean isNullFirst) {
        class IndexedValue {
            private final int index;
            private final Object value;

            IndexedValue(int index, Object value) {
                this.index = index;
                this.value = value;
            }
            int getIndex() { return index; }
            Object getValue() {return value; }
        }
        class IndexedValueCompare implements Comparator<IndexedValue> {
            @Override
            public int compare(IndexedValue value1, IndexedValue value2) {
                Object obj1 = value1.getValue();
                Object obj2 = value2.getValue();

                if (obj1 == null && obj2 == null) {
                    return  0;
                } else if (obj1 == null) {
                    return isNullFirst ? -1 : 1;
                } else if (obj2 == null) {
                    return isNullFirst ? 1 : -1;
                }

                int result;
                if (obj1 instanceof Integer) {
                    result = Integer.compare((Integer) obj1, (Integer) obj2);
                } else if (obj1 instanceof  Double) {
                    result = Double.compare((Double) obj1, (Double) obj2);
                } else {
                    String string1 = (String) obj1, string2 = (String) obj2;
                    result = string1.compareTo(string2);
                }
                return isAscending ? result : -result;
            }
        }
        List<Object> columnValues = new ArrayList<>();
        for (int i = 0; i < columns.get(byIndexOfColumn).count(); i++) {
            columnValues.add(columns.get(byIndexOfColumn).getValue(i));
        }
        List<IndexedValue> indexedValues = new ArrayList<>();
        for(int i = 0; i < columnValues.size(); i++) {
            indexedValues.add(new IndexedValue(i, columnValues.get(i)));
        }
        Collections.sort(indexedValues, new IndexedValueCompare());

        List<Integer> sortedIndices = new ArrayList<>();
        for (IndexedValue indexedValue : indexedValues) {
            sortedIndices.add(indexedValue.getIndex());
        }
        for (Column column : columns) {
            List<Object> sortedValues = new ArrayList<>();
            for (int index : sortedIndices) {
                sortedValues.add(column.getValue(index));
            }
            Object[] newColumn = sortedValues.toArray();
            Column sortedColumn = new ColumnImpl(column.getHeader(), newColumn);
            columns.set(columns.indexOf(column), sortedColumn);
        }
        return this;
    }

    @Override
    public int getRowCount() {
        if (columns.isEmpty()) { return 0; }
        else { return columns.get(0).count(); }
    }

    @Override
    public int getColumnCount() { return columns.size(); }

    @Override
    public Column getColumn(int index) { return columns.get(index); }

    @Override
    public Column getColumn(String name) {
        for (Column column : columns) {
            if (column.getHeader().equals(name)) {
                return column;
            }
        }
        throw new IllegalArgumentException("Column with name '"+name + "'does not exist.");
    }

    @Override
    public Table crossJoin(Table rightTable) {
        String newName = "crossJoined (" + name + "-" + rightTable.getName() + ")";
        List<Column> newColumns = new ArrayList<>();

        // header들 생성
        List<String> headers = new ArrayList<>();
        for (int i = 0; i < getColumnCount(); i++)
            headers.add(name + "." + getColumn(i).getHeader());
        for (int i = 0; i < rightTable.getColumnCount(); i++)
            headers.add(rightTable.getName() + "." + rightTable.getColumn(i).getHeader());

        // Object[]들 생성
        List<Object[]> columnValues = new ArrayList<>();
        for (int i = 0; i < getColumnCount() + rightTable.getColumnCount(); i++)
            columnValues.add(new Object[getRowCount() * rightTable.getRowCount()]);

        // crossJoining
        int count = 0;
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < rightTable.getRowCount(); j++) {
                for (Column column : columns) {
                    int indexOfColumn = columns.indexOf(column);
                    columnValues.get(indexOfColumn)[count] = column.getValue(i);
                }
                for (int k = 0; k < rightTable.getColumnCount(); k++) {
                    int indexOfColumn = getColumnCount() + k;
                    columnValues.get(indexOfColumn)[count] = rightTable.getColumn(k).getValue(j);
                }
                count++;
            }
        }
        // columns 생성
        for (Object[] columnValue : columnValues) {
            String header = headers.get(columnValues.indexOf(columnValue));
            Column column = new ColumnImpl(header, columnValue);
            newColumns.add(column);
        }

        return new TableImpl(newName, newColumns);
    }

    @Override
    public Table innerJoin(Table rightTable, List<JoinColumn> joinColumns) {
        String newName = "innerJoined (" + name + "-" + rightTable.getName() + ")";
        Table crossJoinedTable = crossJoin(rightTable);

        // header들 생성
        List<String> headers = new ArrayList<>();
        for (int i = 0; i < getColumnCount(); i++)
            headers.add(name + "." + getColumn(i).getHeader());
        for (int i = 0; i < rightTable.getColumnCount(); i++)
            headers.add(rightTable.getName() + "." + rightTable.getColumn(i).getHeader());

        // Object[]들 생성
        List<Object[]> columnValues = new ArrayList<>();
        for (int i = 0; i < getColumnCount() + rightTable.getColumnCount(); i++)
            columnValues.add(new Object[getRowCount() * rightTable.getRowCount()]);

        // innerJoining
        List<Integer> matchingIndices = new ArrayList<>();
        for (int i = 0; i < crossJoinedTable.getRowCount(); i++) {
            boolean isCorrect = true;
            for (JoinColumn joinColumn : joinColumns) {
                String columnNameOfThisTable = joinColumn.getColumnOfThisTable();
                String columnNameOfAnotherTable = joinColumn.getColumnOfAnotherTable();
                int columnIndexOfThisTable = columns.indexOf(getColumn(columnNameOfThisTable));
                int columnIndexOfAnotherTable = 0;
                for (int j = 0; j < rightTable.getColumnCount(); j++) {
                    if (rightTable.getColumn(j).getHeader().equals(columnNameOfAnotherTable)) {
                        columnIndexOfAnotherTable = getColumnCount() + j;
                        break;
                    }
                }
                String valueOfThisTable = crossJoinedTable.getColumn(columnIndexOfThisTable).getValue(i);
                String valueOfAnotherTable = crossJoinedTable.getColumn(columnIndexOfAnotherTable).getValue(i);
                if (!valueOfThisTable.equals(valueOfAnotherTable)) {
                    isCorrect = false; break;
                }
            }
            if (isCorrect) {
                matchingIndices.add(i);
            }
        }
        // indecies 언박싱
        int[] intArray = new int[matchingIndices.size()];
        for (int i = 0; i < matchingIndices.size(); i++) {
            intArray[i] = matchingIndices.get(i);
        }

        return crossJoinedTable.selectRowsAt(intArray);
    }

//    @Override
//    public Table outerJoin(Table rightTable, List<JoinColumn> joinColumns) {}
//
//    @Override
//    public Table fullOuterJoin(Table rightTable, List<JoinColumn> joinColumns) {}

}
