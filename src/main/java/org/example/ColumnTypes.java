package org.example;

public enum ColumnTypes {
    Numerical("1", "7", "8", "9");

    private String[] columns;

    ColumnTypes(String... columns) {
        this.columns = columns;
    }

    public String[] getColors() {
        return columns;
    }


    public static boolean isNumericField(String value) {
        for (String col : Numerical.columns) {
            if (col.equals(value))
                return true;
        }
        return false;
    }
// public static boolean isNumericField(String value) {
//  for (int number : ColumnTypes.NUMERICAL.) {
//   if (number.equals(value)) {
//    return true;
//   }
//  }
//  return false;
// }
}
