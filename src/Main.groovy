class Main {
    static void main(String[] args) {
        def list1 = [1, 2, 3, 4]
        def list2 = [2, 3, 4, 5]
        def set = list1 + list2 as Set
        println set
    }
}
