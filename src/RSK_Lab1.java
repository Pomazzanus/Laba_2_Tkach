import java.util.*;

public class RSK_Lab1 {
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);

        String[][] matrix = initializeArray();

        String[] array = getDistinctElements(matrix);

        printArray(array);
        System.out.println();
        System.out.println(array.length);
        System.out.println();

        int[][] adjacencyMatrix = fillAdjacencyMatrix(matrix, array);
        printArrayJagged(adjacencyMatrix);
        System.out.println();

        ArrayList<ArrayList<Integer>> groups;
        groups = returnsGroups(adjacencyMatrix);
    }

    static void printArray(String[] array){
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    static void printArrayJagged(String[][] array){
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void printArrayJagged(int[][] array){
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

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

        return arr;
    }

    static String[] getDistinctElements(String[][] jaggedArray){
        String[] distinctElements = new String[jaggedArray[0].length];

        for (int i = 0; i < jaggedArray[0].length; i++) {
            distinctElements[i] = jaggedArray[0][i];
        }

        boolean isFound = false;
        for (int i = 1; i < jaggedArray.length; i++) {
            for (int j = 0; j < jaggedArray[i].length; j++) {
                for (int k = 0; k < distinctElements.length; k++) {
                    if(jaggedArray[i][j] == distinctElements[k]){
                        isFound = true;
                        break;
                    }
                }

                if(!isFound) {
                    distinctElements = Arrays.copyOf(distinctElements, distinctElements.length + 1);
                    distinctElements[distinctElements.length - 1] = jaggedArray[i][j];
                }
                else{
                    isFound = false;
                }
            }
        }

        return distinctElements;
    }

    static int[][] fillAdjacencyMatrix(String[][] lines, String[] distinctArray){
        int[][] matrix = new int[lines.length][lines.length];

        for (int i = 0; i < lines.length - 1; i++) {
            for (int j = i + 1; j < lines.length; j++) {
                matrix[j][i] = distinctArray.length - getQuantity(lines[i], lines[j]);
            }
        }
        return matrix;
    }

    static int getQuantity(String[] array1, String[] array2){
        Set<String> set1 = new HashSet<String>();
        Set<String> set2 = new HashSet<String>();

        for (int i = 0; i < array1.length; i++) {
            set1.add(array1[i]);
        }
        for (int i = 0; i < array2.length; i++) {
            set2.add(array2[i]);
        }

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        Set<String> intersect = new HashSet<>(set1);
        intersect.retainAll(set2);

        union.removeAll(intersect);

        return union.size();
    }

    static ArrayList<ArrayList<Integer>> returnsGroups(int[][] matrix){
        int indexI = 0, indexJ = 0, groupIndex = -1, max = 0, indexItem = -1;

        ArrayList<ArrayList<Integer>> groups = new ArrayList<ArrayList<Integer>>();
        Queue<int[]> queue = new LinkedList<>();
        ArrayList<Integer> items = new ArrayList<Integer>();
        for (int i = 0; i < matrix.length; i++) {
            items.add(i);
        }

        max = matrix[0][0];
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < i; j++) {
                if(max < matrix[i][j]){
                    max = matrix[i][j];
                    indexI = i;
                    indexJ = j;
                }
            }
        }

        while(max != 0) {
            groups.add(new ArrayList<Integer>());
            groupIndex++;
            queue.add(new int[]{indexI, indexJ});

            while (!queue.isEmpty()) {
                indexI = queue.peek()[0];
                indexJ = queue.peek()[1];
                queue.remove();

                matrix[indexI][indexJ] = 0;
                for (int i = 0; i < indexI; i++) {
                    if (matrix[indexI][i] == max) {
                        queue.add(new int[]{indexI, i});
                    }
                    matrix[indexI][i] = 0;
                }
                for (int i = 0; i < indexJ; i++) {
                    if (matrix[indexJ][i] == max) {
                        queue.add(new int[]{indexJ, i});
                    }
                    matrix[indexJ][i] = 0;
                }
                for (int i = indexJ + 1; i < matrix.length; i++) {
                    if (matrix[i][indexJ] == max) {
                        queue.add(new int[]{i, indexJ});
                    }
                    matrix[i][indexJ] = 0;
                }
                for (int i = indexI + 1; i < matrix.length; i++) {
                    if (matrix[i][indexI] == max) {
                        queue.add(new int[]{i, indexI});
                    }
                    matrix[i][indexI] = 0;
                }

                indexItem = items.indexOf(indexI);
                if(indexItem >= 0){
                    groups.get(groupIndex).add(indexI + 1);
                    items.remove(indexItem);
                }
                indexItem = items.indexOf(indexJ);
                if(indexItem >= 0){
                    groups.get(groupIndex).add(indexJ + 1);
                    items.remove(indexItem);
                }
            }

            max = matrix[0][0];
            for (int i = 1; i < matrix.length; i++) {
                for (int j = 0; j < i; j++) {
                    if(max < matrix[i][j]){
                        max = matrix[i][j];
                        indexI = i;
                        indexJ = j;
                    }
                }
            }
        }

        for (int i = 0; i < items.size(); i++) {
            groups.add(new ArrayList<Integer>());
            groupIndex++;
            groups.get(groupIndex).add(items.get(i) + 1);
            items.remove(i);
        }

        System.out.println("Groups " + (groupIndex + 1));
        for (int i = 0; i < groups.size(); i++) {
            System.out.println(new HashSet<Integer>(groups.get(i)));
        }

        return groups;
    }
}
