
Watch out for result matcher attribute type looks the same, e.g., "2" and 2 (the Integer)

```java
    mockMvc.perform(post("/answer")
                        .sessionAttr("name", "Calvin")
                        .param("question", "2")) // STRING HERE
           .andExpect(model().attribute("question", "2")) // ALSO STRING HERE :thumbsup:
           .andExpect(MockMvcResultMatchers.redirectedUrl("/confirm-finished?question=2"));

    mvcResult = mockMvc.perform(get("/confirm-finished")
                                    .param("question", "2")) // IT'S A STRING HERE
                       .andExpect(MockMvcResultMatchers.status().isOk())
                       .andExpect(view().name("confirm-finished"))
                       .andExpect(model().attribute("question", 2)) // THIS IS AN INTEGER HERE?!
                       .andReturn();
```

Underlying cause is type info is lost: org.springframework.util.ObjectUtils.nullSafeToString(java.lang.Object)