# Leveling Quiz

Points per answer?

## Question Types

{boolean}

{mc} = multiple choice = checkboxes (one or more options can be chosen)
{sc} = single choice = radio button (only one option can be chosen)
  * Randomize option (optional)

{fib} = fill in the blank
  * Regex for answer matching

{essay} = longer answer
  * No automated checking of the answer


## Questions

* {FIB} If you wanted to store lots of Customer objects for easy access via their name, what Java Collections class (data structure) would you use?

* {FIB} What's another data structure could you use?

* {ESSAY} How would you decide whether to use a List or a Map?

* {MC} Which of the following are ways to create a list that can hold Strings (assume Java 8 or later)?

  A. List strings = new ArrayList();
  
  B. List<String> strings = new ArrayList();
  
  C. List<> strings = new ArrayList<String>();
  
  D. List<String> strings = new ArrayList<String>();
  
  E. List<String> strings = new ArrayList<>();

* {MC} Which of the following are *preferred* ways to create a list that can hold Strings?

  A. List strings = new ArrayList();
  
  B. List<String> strings = new ArrayList();

  C. ArrayList<String> strings = new ArrayList<String>();

  D. List<String> strings = new ArrayList<String>();
  
  E. List<String> strings = new ArrayList<>();

* {FIB}

(fill in)
* How would you fix and/or improve the following code so that it properly prints the title of each book and then prints the total price of all books:

  ```java
  
  // improper case for variable name
  // generics don't match
  // can use diamond <> form for ArrayList instantiation
  List<String> Books = new ArrayList<Book>();
  // for 0-10, valueOf is preferred as it uses cached values
  Books.add(new Book("I, Robot", new BigDecimal(10)));
  Books.add(new Book("Singularity Sky", BigDecimal.valueOf(8)));
  // can use foreach instead of indexed loop
  for (int i = 0; i < Books.size(); i++) {
    Book book = Books.get(i);
    // scope of total is wrong
    BigDecimal total = BigDecimal.ZERO;
    System.out.println(book.title());
    // total (BigDecimal) is immutable, need to reassign return
    total.add(book.price());
  }
  System.out.println("Total price: " + total);
  ```
  
  Can also use streams for summing:
  ```java
  BigDecimal total = Books.stream()
                          .map(Book::price)
                          .reduce(BigDecimal.ZERO, BigDecimal::add);
  ```

* {mc} What is printed when this code is executed?

```java
class Audience {
  private int counter = 0;

  public Audience(int initial) {
    counter = initial;
  }

  void display() {
    int counter = 5;
    System.out.println(counter);
  }
  
  public static void main(String[] args){
    int counter = 2;
    new Audience(1).display();
  }
}
```

* {PARSONS} Parson's problem: reorder the following lines of code to make a working program (use Dragula)

> See http://runestoneinteractive.org/build/html/directives.html#parsons-problems


{fib} What diagram is this?

[sequence diagram]