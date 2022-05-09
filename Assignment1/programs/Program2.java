public class Program2{
public static int[] merge(int[] arr1, int[] arr2){
        int size = arr1.length + arr2.length;
        int[] merged = new int[size];
        int idx = 0;
        int i = 0;
        int j = 0;
        while(i < arr1.length && j < arr2.length){
            if(arr1[i] < arr2[j]){
                merged[idx++] = arr1[i++];
            }else{
                merged[idx++] = arr2[j++];
            }
        }
        while (i < arr1.length){
            merged[idx++] = arr1[i++];
        }
        while (j < arr2.length){
            merged[idx++] = arr2[j++];
        }
        return merged;
    }

    public static void main(String[] args) {

        int[] arr1 = {1,3,5,7,8,10};
        int[] arr2 = {2,4,6,9};
        System.out.print("[");
        for(int num : merge(arr1, arr2)){
            System.out.print(num);
            System.out.print(" ");
        }
        System.out.println("]");


    }

}
