public class Add {
    public static void main(String[] args) {
        int add = 0;

        System.out.print("java Add ");

        for(int i=0; i< args.length; i++){
            System.out.print(args[i]+" ");
            try{
                add += Integer.parseInt(args[i]);
            }
            catch (NumberFormatException e){
                continue;
            }

        }
        System.out.print("\n"+add);
    }
}
