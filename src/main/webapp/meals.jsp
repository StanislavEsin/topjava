<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <title>Подсчет калорий</title>
</head>
<body>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

    <div class="jumbotron pt-4">
        <div class="container">
            <h3><a href="index.html">Home</a></h3>
            <h3>Моя еда</h3>
            <br/>

            <form method="post" action="${pageContext.request.contextPath}/meals">
                <input type="hidden" name="method" value="new"/>
                <input type="hidden" name="phase" value="start"/>
                <button type="submit" class="btn btn-primary">
                    <span class="fa fa-plus"></span>
                    Добавить
                </button>
            </form>
            <br/>
            <br/>
            <table class="table table-striped" id="datatable">
                <thead>
                <tr>
                    <th>Дата/Время</th>
                    <th>Описание</th>
                    <th>Калории</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${mealsWithExceed}" var="mealWithExceed">
                        <tr role="row" style="color: ${mealWithExceed.exceed ? "red" : "green"}">
                            <td><javatime:format value="${mealWithExceed.dateTime}" style="MS"/></td>
                            <td>${mealWithExceed.description}</td>
                            <td>${mealWithExceed.calories}</td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/meals">
                                    <input type="hidden" name="method" value="update"/>
                                    <input type="hidden" name="phase" value="start"/>
                                    <input type="hidden" name="meal_id" value="${mealWithExceed.id.toString()}"/>
                                    <button type="submit" class="btn btn-secondary">
                                        <span class="fa fa-plus"></span>
                                        Редактирование
                                    </button>
                                </form>
                            </td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/meals">
                                    <input type="hidden" name="method" value="delete"/>
                                    <input type="hidden" name="meal_id" value="${mealWithExceed.id.toString()}"/>
                                    <button type="submit" class="btn btn-secondary">
                                        <span class="fa fa-plus"></span>
                                        Удалить
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>