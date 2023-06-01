package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

class TableImpl implements Table {
    private String name;
    private List<Column> columns;

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

//    @Override
//    public Table head() {}
//
//    @Override
//    public Table head(int lineCount) {}
//
//    @Override
//    public Table tail() {}
//
//    @Override
//    public Table tail(int lineCount) {}
//
//    @Override
//    public Table selectRows(int beginIndex, int endIndex) {}
//
//    @Override
//    public Table selectRowsAt(int ...indices) {}
//
//    @Override
//    public Table selectColumns(int beginIndex, int endIndex) {}
//
//    @Override
//    public Table selectColumnsAt(int ...indices) {}
//
//    @Override
//    public <T> Table selectRowsBy(String columnName, Predicate<T> predicate) {}
//
//    @Override
//    public Table sort(int byIndexOfColumn, boolean isAscending, boolean isNullFirst) {}
//
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
