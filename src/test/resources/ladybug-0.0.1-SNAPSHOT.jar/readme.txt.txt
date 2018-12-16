Application doesn't support proxy out of the box (probably java parameteres would work)
After initial startup (java -jar ladybug-0.0.1-SNAPSHOT.jar) it will download mongodb database installer so first run is slow
After startup UI will be visible at local adress: http://localhost:8080
Login parameters for different roles:
- admin: admin/test
- employee : employee/test
- client: client/test 

DB will reset it's content after hitting (CTRL+C)

Rest API at http://localhost:8080/swagger-ui.html