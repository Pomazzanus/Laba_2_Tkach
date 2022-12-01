import java.util.*;

public class RSK_Lab1 {
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);
        String[][] matrix = Repository.initializeArray();

        String[] array = getDistinctElements(matrix);
        System.out.println("____________\nLab_1\n");
        printArrayJagged(Repository.initializeArray());
        System.out.println();
        printArray(array);
        System.out.println();
        System.out.println(array.length);
        System.out.println();

        int[][] adjacencyMatrix = fillAdjacencyMatrix(matrix, array);
        printArrayJagged(adjacencyMatrix);
        System.out.println();

        ArrayList<ArrayList<Integer>> groups;
        groups = returnsGroups(adjacencyMatrix);

        System.out.println("____________\nLab_2\n");
        String[][] groupArr = new String[groups.size()][];
        for(int i = 0; i < groups.size(); i++){
            Collections.sort(groups.get(i));
            String[][] tempArr = new String[groups.get(i).size()][];
            for(int j = 0; j < groups.get(i).size();j++){
                tempArr[j] = matrix[groups.get(i).get(j)-1];  //тут мы создаем временный массивчик куда пихаем строчки из нашей матрицы по номеру группы
            }
            System.out.print(groups.get(i));
            String[] distArr = getDistinctElements(tempArr);  //а тут находим уникальные операции для них
            groupArr[i] = distArr;
            System.out.println(" - " + distArr.length + " різнотипних операцій");
            printArray(distArr);
            System.out.println();
        }
        groupArr = sortArray(groupArr, groups);  // а тут мы их сортируем по количеству уникальных операций
        for (int i = 0; i < groupArr.length; i++) {
            System.out.println();
            System.out.println(groups.get(i));
            for (int j = 0; j < groupArr[i].length; j++) {
                System.out.print(groupArr[i][j] + " ");
            }
            System.out.println();
        }
        ArrayList<ArrayList<String>> elementsInGroup = new ArrayList<ArrayList<String>>();

        for(int i = 0; i < groupArr.length; i++){
            ArrayList<String> iList = new ArrayList<String>();
            for (int j = 0; j<groupArr[i].length; j++){
                iList.add(groupArr[i][j]);
            }
            elementsInGroup.add(iList);
        }

        boolean isAbsorbGroups = true;
        boolean isAbsorbElement = true;
        ArrayList<ArrayList<String>> clarificationElementsInGroup = new ArrayList<>();
        ArrayList<ArrayList<Integer>> clarificationGroups = new ArrayList<>();

        do {
            for(int i = 1; i<groups.size() - 1; i++) {  //Етап 1
                for(String element : elementsInGroup.get(i)) {
                    isAbsorbGroups = true;

                    if (!elementsInGroup.get(0).contains(element)) {
                        isAbsorbGroups = false;
                        break;
                    }
                }
                if (isAbsorbGroups) {
                    for (int j = 0; j < groups.get(i).size(); j++) {
                        groups.get(0).add(groups.get(i).get(j));
                    }
                    groups.remove(i);
                    elementsInGroup.remove(i);
                }
            }

            for(int i = 1; i<groups.size() - 1; i++) {  //Етап 2
                for (int j = 0; j < groups.get(i).size(); j++) {
                    for (String element : Repository.initializeArray()[(groups.get(i).get(j))-1]) {
                        isAbsorbElement = true;
                        if (!elementsInGroup.get(0).contains(element)) {
                            isAbsorbElement = false;
                            break;
                        }
                    }
                    if (isAbsorbElement) {
                        groups.get(0).add(groups.get(i).get(j));
                        groups.get(i).remove(groups.get(i).get(j));
                        elementsInGroup.get(i).clear();
                        for (int newGroup : groups.get(i)) {
                            for (String element : Repository.initializeArray()[newGroup-1]) {
                                if (!elementsInGroup.get(i).contains(element)) {
                                    elementsInGroup.get(i).add(element);
                                }
                            }
                        }
                    }
                }
            }
            clarificationElementsInGroup.add(elementsInGroup.get(0));
            elementsInGroup.remove(0);
            clarificationGroups.add(groups.get(0));
            groups.remove(0);

            elementsInGroup = sortArrayList(elementsInGroup, groups);

        }while(groups.size() > 0);


        /*for(int i = 0; i<groupArr.length; i++){  // вот тут начинается задание со слиянием строк
            System.out.println(i);
            String[] tempArray = groupArr[i];
            for(int k = i+1; k<groupArr[k-1].length; k++){

                String[][] tempArray1 = new String[2][];
                tempArray1[0] = tempArray;
                tempArray1[1] = groupArr[k];
                if(getDistinctElements(tempArray1).length == tempArray.length){
                    String[][] tempGroup = groupArr;
                    for (int n = 0; n<groupArr.length; n++){
                        if(groupArr[k].equals(tempGroup[n])){
                            ArrayList<Integer> addList = new ArrayList<Integer>();
                            addList.addAll(groups.get(n));
                            addList.addAll(groups.get(i));
                            groups.set(i, addList);
                            elementsInGroup.remove(n);
                            Collections.sort(groups.get(i));
                            groups.remove(n);
                        }
                    }
                }
            }
        }*/

        System.out.println("\n");

        for (int i = 0; i < clarificationGroups.size(); i++) {
            Collections.sort(clarificationGroups.get(i));
        }

        //printArrayListOfElements(elementsInGroup, groups);
        printArrayListOfElements(clarificationElementsInGroup, clarificationGroups);
    }

    static void printArrayListOfElements (List<ArrayList<String>> elementInGroup) {
        for (int i = 0; i < elementInGroup.size(); i++) {
            for (int j = 0; j < elementInGroup.get(i).size(); j++) {
                System.out.print(elementInGroup.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
    static void printArrayListOfElements (List<ArrayList<String>> elementInGroup, ArrayList<ArrayList<Integer>> groups) {
        for (int i = 0; i < elementInGroup.size(); i++) {
            System.out.println();
            System.out.println(groups.get(i));
            for (int j = 0; j < elementInGroup.get(i).size(); j++) {
                System.out.print(elementInGroup.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
    static String[][] sortArray(String[][] array, ArrayList<ArrayList<Integer>> groups){
        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < array.length; i++) {
                if (array[i].length > array[i - 1].length) {
                    String[] tmp = array[i];
                    array[i] = array[i - 1];
                    array[i - 1] = tmp;
                    Collections.swap(groups, i, i-1);
                    needIteration = true;
                }
            }
        }
        return array;
    }

    static ArrayList<ArrayList<String>> sortArrayList(ArrayList<ArrayList<String>> array, ArrayList<ArrayList<Integer>> groups){
        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < array.size(); i++) {
                if (array.get(i).size() > array.get(i - 1).size()) {
                    ArrayList<String> tmp = array.get(i);
                    //array[i] = array[i - 1];
                    array.set(i, array.get(i - 1));
                    //array[i - 1] = tmp;
                    array.set(i - 1, tmp);
                    Collections.swap(groups, i, i-1);
                    needIteration = true;
                }
            }
        }
        return array;
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
