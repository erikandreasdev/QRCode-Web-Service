class ChristmasTree {

    private String color;

    public ChristmasTree(String color) {
        this.color = color;
    }

    public void putTreeTopper(String color) {
        TreeTopper treeTopper = new TreeTopper(color);
        treeTopper.sparkle();
    }

    public class TreeTopper {

        private String color;

        public TreeTopper(String color) {
            this.color = color;
        }

        public void sparkle() {
            System.out.printf("Sparkling %s tree topper looks stunning with %s Christmas tree!",
                              this.color,
                              ChristmasTree.this.color);
        }

    }

}

// this code should work
class CreateHoliday {

    public static void main(String[] args) {

        ChristmasTree christmasTree = new ChristmasTree("green");
        christmasTree.putTreeTopper("silver");
    }

}
