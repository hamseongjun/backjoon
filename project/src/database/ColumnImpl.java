package database;

class ColumnImpl implements Column {
    private String header;
    private Object[] values;

    public ColumnImpl(String header, Object[] values) {
        this.header = header;
        this.values = values;
    }

    @Override
    public String getHeader() {
        return header;
    }

    @Override
    public String getValue(int index) {
        if (String.valueOf(values[index]).equals("")) { return null; }
        return String.valueOf(values[index]);
    }

//    @Override
//    public <T extends Number> T getValue(int index, Class<T> t) {}
//
//    @Override
//    public void setValue(int index, String value) {}
//
//    @Override
//    public void setValue(int index, int value) {}

    @Override
    public int count() {
        return values.length;
    }

    @Override
    public void show() {
        int width = getColumnWidth();
        System.out.printf("%" + width + "d\n", header);
        for (Object value : values) {
            System.out.printf("%" + width + "d\n", value);
        }
    }

    private int getColumnWidth() {
        int maxLength = header.length();
        for(Object value : values) {
            String string = String.valueOf(value);
            if (maxLength < string.length()) { maxLength = string.length(); }
        }
        return maxLength;
    }

//    @Override
//    public boolean isNumericColumn() {}
//
//    @Override
//    public long getNullCount() {}

}
