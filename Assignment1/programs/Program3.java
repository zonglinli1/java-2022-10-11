public class Program3{

  public static void main(String[] args) {
        int[] arr = {1,3,5,7,8,100,42,73,74,9,24,99,101};

        System.out.format("the second largest: %d\n", secondLargest(arr));
    }
    public static int secondLargest(int[] arr){
        int max = arr[0];
        int second = Integer.MIN_VALUE;
        for(int i = 1; i < arr.length; i++){
            if(arr[i] > max){
                second = max;
                max = arr[i];
            }else if(arr[i] > second){
                second = arr[i];
            }
        }
        return second;
    }

}
