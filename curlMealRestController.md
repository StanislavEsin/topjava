Meal handling

- <a href="http://localhost:8080/topjava/rest/profile/meals">Meal list</a>
- <a href="http://localhost:8080/topjava/rest/profile/meals/100005">Meal 100005</a>
- <a href="http://localhost:8080/topjava/rest/profile/meals/filter?startDateTime=2015-05-31T12:00:00&endDateTime=2015-05-31T20:00:00">Meal from 2015-05-31 12:00:00 to 2015-05-31 20:00:00</a>
- <a href="http://localhost:8080/topjava/rest/profile/meals/between?startDate=2015-05-31&startTime=12:00:00&endDate=2015-05-31&endTime=20:00:00">Meal from date 2015-05-31 time 12:00:00 to date 2015-05-31 time 20:00:00</a>
- <a href="http://localhost:8080/topjava/rest/profile/meals/100005">Delete meal 100005</a>

CURL:

    curl -X GET 'http://localhost:8080/topjava/rest/profile/meals'
    curl -X GET 'http://localhost:8080/topjava/rest/profile/meals/100005'
    curl -X POST 'http://localhost:8080/topjava/rest/profile/meals/filter?startDateTime=2015-05-31T12:00:00&endDateTime=2015-05-31T20:00:00'
    curl -X POST 'http://localhost:8080/topjava/rest/profile/meals/between?startDate=2015-05-31&startTime=12:00:00&endDate=2015-05-31&endTime=20:00:00'
    curl -X POST 'http://localhost:8080/topjava/rest/profile/meals' -d '{"dateTime" : "2018-08-19T17:17:00","description" : "createTest","calories" : 1000}' -H 'Content-Type: application/json'
    curl -X PUT 'http://localhost:8080/topjava/rest/profile/meals/100005' -d '{"dateTime" : "2015-05-31T10:00:00","description" : "updateTest","calories" : 1200}' -H 'Content-Type: application/json'
    curl -X DELETE 'http://localhost:8080/topjava/rest/profile/meals/100005'