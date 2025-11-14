public class Cat extends Pet {
    public Cat(String name, int age) {
        super(name, age);
    }

    @Override
    public String getPetType() {
        return "Cat";
    }
}
