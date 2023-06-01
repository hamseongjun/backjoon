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
        if (values[index] == null) { return null; }
        else { return String.valueOf(values[index]); }
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
        System.out.println(header);
        for (Object value : values) {
            System.out.println(value);
        }
    }

    @Override
    public boolean isNumericColumn() {
        for (int i = 0; i < count(); i++) {
            if (getValue(i) == null) { continue; }
            try {
                Integer value = Integer.valueOf(getValue(i));
                return true;
            } catch (NumberFormatException nonInt) {
                try {
                    Double value = Double.valueOf(getValue(i));
                    return true;
                }
                catch (NumberFormatException nonDouble) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public long getNullCount() {
        int count = 0;
        for (int i = 0; i < count(); i++) {
            if(values[i] != null) { count++; }
        }
        return count;
    }

}
