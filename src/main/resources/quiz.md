|mc|E| Take a look at these two classes:

```
class Equity {
  public Equity(String name) {
  }
}

class Stock extends Equity {
}
```

What's wrong with this code?

===

A. Won't compile: Stock doesn't have a default constructor

B. Won't compile: Equity doesn't have a default constructor

C. Can be fixed by adding this code to Stock: `Stock(String name) { }`

D. Can be fixed by adding this code to Stock: `Stock(String name) { super(name); }`

E. I really don't know

---

|mc|A,B,D,E| Which of the following are ways to create a list that can hold Strings (assume Java 8 or later)?

===

A. `List strings = new ArrayList();`

B. `List<String> strings = new ArrayList();`

C. `List<> strings = new ArrayList<String>();`

D. `List<String> strings = new ArrayList<String>();`

E. `List<String> strings = new ArrayList<>();`

---

|mc|E| Which of the following are _**preferred**_ ways to create a list that can hold `String`s (assume Java 8 or later)?

===

A. `List strings = new ArrayList();`

B. `List<String> strings = new ArrayList();`

C. `ArrayList<String> strings = new ArrayList<String>();`

D. `List<String> strings = new ArrayList<String>();`

E. `List<String> strings = new ArrayList<>();`

---

|fib|map,hashmap| If you wanted to store lots of Customer objects for easy access via their name,
what Java Collections type (data structure) would you use?
