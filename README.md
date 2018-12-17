## This is demo project in Gatling for Laydybug application 
This is pure demo project for learning purposes. Feel free to comment or use it.

Tested application can be found in resources *(src\test\resources\ladybug-0.0.1-SNAPSHOT.jar\)*

Gatling sample report with analysis can be found under this path: src\test\resources\Test_results\

I have written few thoughts related to implementation process (what to avoid or what are the roadblocks)  
*(src\test\scala\com\laydybug\timeEaters.txt)*


Implemented load scenario has two Actors:
-  As a **client** place order for 2 products
-  As a **employee** accept this order


ToDo:   
    
    1.In ideal world such scenario should work in a fashion where users would have random orders with random product 
    amount. This is not the case here - I used order_request_example.txt to execute request with 2 static products
    I thought that learning how to create proper json is not the case here (it can be done other time).
    
    2. Confirm product by employee has "assumption"/"workaround" - because lack of time I didn't had chance to implement 
    paging. Instead I used trick to get 500/1000 orders on single page. The problem with that approach is that once all 
    orders on first page will be confirmed test will start to fail.
    
    3. There is no proper exit/"fail fast" strategy - sometimes test fails in random manner and threads keep working.
    This should be fixed to avoid exceptions in build log.
    
    4. Observations during programming can be found in timeEaters.txt file. Right now I would say that writing tests
    in this framework is complicated especially when some "random" issue will show up.
    Scala tutorial would probably be helpful. There were multiple issues without any clear solution that could be easily 
    found in documentation.
    
# Ladybug application
This is an application that can used to teach about testing (it is in polish language). There are multiple issues 
visible on first sight ;) 

Application was created by **Emilia Lendzion-Barszcz** on "*hackathon*" orginzed by Girls Who Test organization and javagirl. 
You can find more about her at blog javagirl.pl