public class Repository {
    static String[][] initializeArray(){
        String[][] jaggedArray = new String[14][];
        jaggedArray[0] = new String[] {"T3","T1","C1","T2","C2"};
        jaggedArray[1] = new String[] {"T3","C2","T1","C1","T2","F1","T5"};
        jaggedArray[2] = new String[] {"T4","T1","C1","T2","F1","T5"};
        jaggedArray[3] = new String[] {"T4","T1","C1","T2","C2","T5"};
        jaggedArray[4] = new String[] {"T1","C1","T2","T3","T4"};
        jaggedArray[5] = new String[] {"T1","C1","T2","F1","F2"};
        jaggedArray[6] = new String[] {"T1","C1","T2","C2","T5"};
        jaggedArray[7] = new String[] {"T1","F2","P1","T2","T3","T4","T5"};
        jaggedArray[8] = new String[] {"T1","P1","F2","F3"};
        jaggedArray[9] = new String[] {"T1","P1","T2","T3","F2","T5"};
        jaggedArray[10] = new String[] {"T3","T4","T2","F3","P1","F2"};
        jaggedArray[11] = new String[] {"T3","T1","F2","P1","T4","T5"};
        jaggedArray[12] = new String[] {"T3","C1","T1","F3","P1","F2","T5"};
        jaggedArray[13] = new String[] {"T4","C1","T1","F2","P1"};

        String[][] arr = new String[][]
                {
                        {"T1","F1","T4","C1","C2"},
                        {"T4","C1","C2","F1","F2"},
                        {"T1","T2","T4"},
                        {"T1","T4","C1"},
                        {"T4"}
                };

        String[][] arr1 = new String[][]
                {
                        {"T1","T2","T3","C1","C2"},
                        {"T2","T3","C1"},
                        {"T3","C2"},
                        {"T1","T3"},
                        {"T2","C1","C2"},
                        {"T3"}
                };
        String[][] arr2 = new String[][]
                {
                        {"T1","F1","T4","C1","C2"},
                        {"T4","C1","C2","F1","F2"},
                        {"T1","T2","T4"},
                        {"T1","T4","C1"},
                        {"T4"}
                };

        return jaggedArray;
    }
}
