package database;

import java.util.*;
import java.util.function.Predicate;

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

    @Override
    public <T> Table selectRowsBy(String columnName, Predicate<T> predicate) {
        List<Integer> filteredIndices = new ArrayList<>();
        Column selectedColumn = getColumn(columnName);
        boolean isNumericColumn = selectedColumn.isNumericColumn();

        if (isNumericColumn) {
            for (int i = 0; i < getRowCount(); i++) {
                    if (predicate.test((T) selectedColumn.getValue(i, Integer.class)))
                        filteredIndices.add(i);
                }
            }
        else {
            for (int i = 0; i < getRowCount(); i++) {
                if (predicate.test((T) selectedColumn.getValue(i)))
                    filteredIndices.add(i);
            }
        }

        int[] selectedRows = new int[filteredIndices.size()];
        for (int i = 0; i < filteredIndices.size(); i++) {
            selectedRows[i] = filteredIndices.get(i);
        }

        return  selectRowsAt(selectedRows);
    }

    @Override
    public Table sort(int byIndexOfColumn, boolean isAscending, boolean isNullFirst) {
        List<Integer> nullIndices = new ArrayList<>();
        List<Integer> existIndices = new ArrayList<>();
        List<String> stringList = new ArrayList<>();

        // 행 번호 & values 저장
        for (int i = 0; i < columns.get(byIndexOfColumn).count(); i++) {
            if (columns.get(byIndexOfColumn).getValue(i) == null)
                nullIndices.add(i);
            else {
                existIndices.add(i);
                stringList.add(columns.get(byIndexOfColumn).getValue(i));
            }
        }
        // (배열로 가공)
        String[] stringArr = stringList.toArray(new String[0]);
        Integer[] indicesArr = existIndices.toArray(new Integer[0]);
        // 1. 정렬
        quickSort(stringArr, 0, stringArr.length-1, indicesArr);

        // (리스트로 가공)
        existIndices = Arrays.asList(indicesArr);
        existIndices = new ArrayList<>(existIndices);
        // 2. 오름 or 내림차순 선택
        if (!isAscending) {
            Collections.reverse(existIndices);
        }

        // 3. null first or last 선택
        if (isNullFirst) {
            nullIndices.addAll(existIndices);
            indicesArr = nullIndices.toArray(new Integer[0]);
        } else {
            existIndices.addAll(nullIndices);
            indicesArr = existIndices.toArray(new Integer[0]);
        }
        // Integer 언박싱
        int[] intArray = new int[indicesArr.length];
        for (int i = 0; i < indicesArr.length; i++) {
            intArray[i] = indicesArr[i];
        }

        Table sortedTable = selectRowsAt(intArray);
        for (int i = 0; i < columns.size(); i++) {
            columns.set(i, sortedTable.getColumn(i));
        }
        return this;
    }

    private void quickSort(String[] arr, int left, int right, Integer[] indexArr) {
        if (left >= right)
            return;
        int pivotIndex = partition(arr, left, right, indexArr);
        quickSort(arr, left, pivotIndex - 1, indexArr);
        quickSort(arr, pivotIndex + 1, right, indexArr);
    }

    private int partition(String[] arr, int left, int right, Integer[] indexArr) {
        int lo = left, hi = right;
        String pivotIndex = arr[left];

        while (lo < hi) {
            while (arr[hi].compareTo(pivotIndex) > 0 && lo < hi) {
                hi--;
            }
            while ((arr[lo].compareTo(pivotIndex) <= 0) && lo < hi) {
                lo++;
            }
            swap(arr, lo, hi);
            swap(indexArr, lo, hi);
        }
        swap(arr, left, lo);
        swap(indexArr, left, lo);
        return lo;
    }

    private void swap(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
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
        throw new IllegalArgumentException("Column with name '"+ name + "'does not exist.");
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
        Table crossJoinedTable = crossJoin(rightTable);

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
                if (valueOfThisTable == null || valueOfAnotherTable == null) {
                    isCorrect = false; break;
                } else if (!valueOfThisTable.equals(valueOfAnotherTable)) {
                    isCorrect = false; break;
                }
            }
            if (isCorrect) {
                matchingIndices.add(i);
            }
        }
        // indices 언박싱
        int[] intArray = new int[matchingIndices.size()];
        for (int i = 0; i < matchingIndices.size(); i++) {
            intArray[i] = matchingIndices.get(i);
        }

        return crossJoinedTable.selectRowsAt(intArray);
    }

    @Override
    public Table outerJoin(Table rightTable, List<JoinColumn> joinColumns) {
        String newName = "outerJoined (" + name + "-" + rightTable.getName() + ")";

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
            columnValues.add(new Object[getRowCount()]);

        // indices 생성
        List<Integer> matchingIndices = new ArrayList<>();
        List<Integer> matchingIndicesOfRightTable = new ArrayList<>();
        List<Integer> noMatchingIndices = new ArrayList<>();
        for (int i = 0; i < getRowCount(); i++) {
            boolean isCorrect = false, noMatch = true;
            for (int j = 0; j < rightTable.getRowCount(); j++) {
                isCorrect = true;
                for (JoinColumn joinColumn : joinColumns) {
                    String columnNameOfThisTable = joinColumn.getColumnOfThisTable();
                    String columnNameOfAnotherTable = joinColumn.getColumnOfAnotherTable();
                    String valueOfThisTable = getColumn(columnNameOfThisTable).getValue(i);
                    String valueOfAnotherTable = rightTable.getColumn(columnNameOfAnotherTable).getValue(j);
                    if (valueOfThisTable == null || valueOfAnotherTable == null) {
                        isCorrect = false; break;
                    } else if (!valueOfThisTable.equals(valueOfAnotherTable)) {
                        isCorrect = false; break;
                    }
                }
                if (isCorrect) {
                    matchingIndices.add(i); matchingIndicesOfRightTable.add(j);
                    noMatch = false;
                }
            }
            if(noMatch)
                noMatchingIndices.add(i);
        }
        // inner join
        for (Integer matchingIndex : matchingIndices) {
            int m = matchingIndices.indexOf(matchingIndex);
            for (Column column : columns) {
                int indexOfColumn = columns.indexOf(column);
                columnValues.get(indexOfColumn)[m] = column.getValue(matchingIndex);
            }
        }
        for (Integer matchingIndexOfRightTable : matchingIndicesOfRightTable) {
            int m = matchingIndicesOfRightTable.indexOf(matchingIndexOfRightTable);
            for (int i = 0; i < rightTable.getColumnCount(); i++) {
                int indexOfColumn = getColumnCount() + i;
                columnValues.get(indexOfColumn)[m] = rightTable.getColumn(i).getValue(matchingIndexOfRightTable);
            }
        }
        // outer join
        for (Integer noMatchingIndex : noMatchingIndices) {
            int n = noMatchingIndices.indexOf(noMatchingIndex) + matchingIndices.size();
            for (Column column : columns) {
                int indexOfColumn = columns.indexOf(column);
                columnValues.get(indexOfColumn)[n] = column.getValue(noMatchingIndex);
            }
            for (int i = 0; i < rightTable.getColumnCount(); i++) {
                int indexOfColumn = getColumnCount() + i;
                columnValues.get(indexOfColumn)[n] = null;
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
    public Table fullOuterJoin(Table rightTable, List<JoinColumn> joinColumns) {
        String newName = "fullOuterJoin (" + name + "-" + rightTable.getName() + ")";

        List<Column> newColumns = new ArrayList<>();

        // header들 생성
        List<String> headers = new ArrayList<>();
        for (int i = 0; i < getColumnCount(); i++)
            headers.add(name + "." + getColumn(i).getHeader());
        for (int i = 0; i < rightTable.getColumnCount(); i++)
            headers.add(rightTable.getName() + "." + rightTable.getColumn(i).getHeader());

        // indices 생성
        List<Integer> matchingIndices = new ArrayList<>();
        List<Integer> matchingIndicesOfRightTable = new ArrayList<>();
        List<Integer> noMatchingIndices = new ArrayList<>();
        for (int i = 0; i < getRowCount(); i++) {
            boolean isCorrect = false, noMatch = true;
            for (int j = 0; j < rightTable.getRowCount(); j++) {
                isCorrect = true;
                for (JoinColumn joinColumn : joinColumns) {
                    String columnNameOfThisTable = joinColumn.getColumnOfThisTable();
                    String columnNameOfAnotherTable = joinColumn.getColumnOfAnotherTable();
                    String valueOfThisTable = getColumn(columnNameOfThisTable).getValue(i);
                    String valueOfAnotherTable = rightTable.getColumn(columnNameOfAnotherTable).getValue(j);
                    if (valueOfThisTable == null || valueOfAnotherTable == null) {
                        isCorrect = false; break;
                    } else if (!valueOfThisTable.equals(valueOfAnotherTable)) {
                        isCorrect = false; break;
                    }
                }
                if (isCorrect) {
                    matchingIndices.add(i); matchingIndicesOfRightTable.add(j);
                    noMatch = false;
                }
            }
            if(noMatch)
                noMatchingIndices.add(i);
        }
        // right noMatchingIndices 생성
        List<Integer> noMatchingIndicesOfRightTable = new ArrayList<>();
        for (int i = 0; i < rightTable.getRowCount(); i++) {
            if (!matchingIndicesOfRightTable.contains(i)) {
                noMatchingIndicesOfRightTable.add(i);
            }
        }
        // Object[]들 생성
        List<Object[]> columnValues = new ArrayList<>();
        for (int i = 0; i < getColumnCount() + rightTable.getColumnCount(); i++)
            columnValues.add(new Object[getRowCount() + noMatchingIndicesOfRightTable.size()]);

        // inner join
        for (Integer matchingIndex : matchingIndices) {
            int m = matchingIndices.indexOf(matchingIndex);
            for (Column column : columns) {
                int indexOfColumn = columns.indexOf(column);
                columnValues.get(indexOfColumn)[m] = column.getValue(matchingIndex);
            }
        }
        for (Integer matchingIndexOfRightTable : matchingIndicesOfRightTable) {
            int m = matchingIndicesOfRightTable.indexOf(matchingIndexOfRightTable);
            for (int i = 0; i < rightTable.getColumnCount(); i++) {
                int indexOfColumn = getColumnCount() + i;
                columnValues.get(indexOfColumn)[m] = rightTable.getColumn(i).getValue(matchingIndexOfRightTable);
            }
        }
        // outer join
        for (Integer noMatchingIndex : noMatchingIndices) {
            int n = matchingIndices.size() + noMatchingIndices.indexOf(noMatchingIndex);
            for (Column column : columns) {
                int indexOfColumn = columns.indexOf(column);
                columnValues.get(indexOfColumn)[n] = column.getValue(noMatchingIndex);
            }
            for (int i = 0; i < rightTable.getColumnCount(); i++) {
                int indexOfColumn = getColumnCount() + i;
                columnValues.get(indexOfColumn)[n] = null;
            }
        }
        for (Integer noMatchingIndex : noMatchingIndicesOfRightTable) {
            int n = matchingIndices.size() + noMatchingIndices.size() + noMatchingIndicesOfRightTable.indexOf(noMatchingIndex);
            for (int i =0; i < getColumnCount(); i++) {
                columnValues.get(i)[n] = null;
            }
            for (int i = 0; i < rightTable.getColumnCount(); i++) {
                int indexOfColumn = getColumnCount() + i;
                columnValues.get(indexOfColumn)[n] = rightTable.getColumn(i).getValue(noMatchingIndex);
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
}
