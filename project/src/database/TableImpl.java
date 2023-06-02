package database;

import java.util.*;

class TableImpl implements Table {
    private final String name;
    private final List<Column> columns;

    TableImpl(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
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
        for (int i = 0; i < columns.get(0).count(); i++) {
            for (Column column : columns) {
                String value = column.getValue(i);
                System.out.printf("%" + widths.get(columns.indexOf(column)) + "s | ", value);
            }
            System.out.println();
        }
    }

    private int getColumnWidth(Column column) {
        int maxLength = column.getHeader().length();
        for (int i = 0; i < column.count(); i++) {
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
        int entries = columns.get(0).count();
        System.out.println("RangeIndex: " + entries + " entries, 0 to " + (entries-1));
        System.out.println("Data Columns (total " + columns.size() + " columns):");

        String[] header = {"#", "Column", "Non-Null Count", "Dtype"};
        int[] maxSize = {header[0].length(), header[1].length(), header[2].length()};
        List<Object[]> rows = new ArrayList<>();

        int intType = 0, stringType = 0;
        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            String columnName = column.getHeader();
            long nonNullCount = column.getNullCount();
            String dtype = null;
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
        List<Column> newColumns = new ArrayList<>();
        for (Column column : columns) {
            int min = Math.min(5, column.count());
            Object[] values = new Object[min];
            for (int i = 0; i < min; i++) {
                values[i] = column.getValue(i);
            }
            newColumns.add(new ColumnImpl(column.getHeader(), values));
        }
        return new TableImpl(name, newColumns);
    }

    @Override
    public Table head(int lineCount) {
        List<Column> newColumns = new ArrayList<>();
        for (Column column : columns) {
            int min = Math.min(lineCount, column.count());
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
        List<Column> newColumns = new ArrayList<>();
        for (Column column : columns) {
            int startIndex = Math.max(0, column.count() - 5);
            int min = Math.min(5, column.count());
            Object[] values = new Object[min];
            for (int i = 0; i < min; i++) {
                values[i] = column.getValue(i+startIndex);
            }
            newColumns.add(new ColumnImpl(column.getHeader(), values));
        }
        return new TableImpl(name, newColumns);
    }

    @Override
    public Table tail(int lineCount) {
        List<Column> newColumns = new ArrayList<>();
        for (Column column : columns) {
            int startIndex = Math.max(0, column.count() - lineCount);
            int min = Math.min(lineCount, column.count());
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
        List<Column> newColumns = new ArrayList<>();
        for (int i = 0; i < endIndex-beginIndex; i++) {
            newColumns.add(columns.get(i+beginIndex));
        }
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
        List<Object> columnValues = extractColumnValues(byIndexOfColumn);
        List<IndexedValue> indexedValues = createIndexedValues(columnValues);
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

    private List<Object> extractColumnValues(int columnIndex) {
        List<Object> values = new ArrayList<>();
        for (int i = 0; i < columns.get(columnIndex).count(); i++) {
            values.add(columns.get(columnIndex).getValue(i));
        } return values;
    }

    private List<IndexedValue> createIndexedValues(List<Object> values) {
        List<IndexedValue> indexedValues = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            indexedValues.add(new IndexedValue(i, values.get(i)));
        } return indexedValues;
    }

    private class IndexedValue {
        private final int index;
        private final Object value;

        IndexedValue(int index, Object value) {
            this.index = index;
            this.value = value;
        }
        int getIndex() { return index; }
        Object getValue() {return value; }
    }

//    @Override
//    public int getRowCount() {}
//
//    @Override
//    public int getColumnCount() {}
//
//    @Override
//    public Column getColumn(int index) {}
//
//    @Override
//    public Column getColumn(String name) {}
//
//    @Override
//    public Table crossJoin(Table rightTable) {}
//
//    @Override
//    public Table innerJoin(Table rightTable, List<JoinColumn> joinColumns) {}
//
//    @Override
//    public Table outerJoin(Table rightTable, List<JoinColumn> joinColumns) {}
//
//    @Override
//    public Table fullOuterJoin(Table rightTable, List<JoinColumn> joinColumns) {}

}
