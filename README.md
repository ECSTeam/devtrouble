Dev Trouble
================


This app can simulate or demonstrate some common developer troubles.

Current troubles simulated:

- Out of memory

### Out of memory

To run locally:

```
$ mvn spring-boot:run
```
Navigate to `http://localhost:8080`

Click the `Out of Memory` link.

Sample output:

```
Output:

Calling oom endpoint...

=================> OOM test started..

Iteration 1 Free Mem: 230440712

Required Memory for next loop: 100

Iteration 2 Free Mem: 230138184

Required Memory for next loop: 500

Iteration 3 Free Mem: 227506728

...
```
