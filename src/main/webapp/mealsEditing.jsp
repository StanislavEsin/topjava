<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <title>Редактирование еды</title>
</head>
<body>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

    <div class="jumbotron pt-4">
        <div class="container">
            <div class="py-5">
                <h3>Редактирование еды</h3>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <form id="edit" method="post" action="${pageContext.request.contextPath}/meals">
                        <input type="hidden" name="method" value="${method}"/>
                        <input type="hidden" name="phase" value="end"/>
                        <input type="hidden" name="meal_id" value="${meal.id.toString()}">

                        <div class="col-md-5">
                            <label for="dateTime">Дата/Время</label>
                            <input type="datetime-local" class="form-control" id="dateTime" name="dateTime" placeholder="Дата/Время" required value="${meal.dateTime}">
                        </div>

                        <div class="col-md-5">
                            <label for="description">Описание</label>
                            <input type="text" class="form-control" id="description" name="description" placeholder="Описание" required value="${meal.description}">
                        </div>

                        <div class="col-md-5">
                            <label for="calories">Описание</label>
                            <input type="number" class="form-control" id="calories" name="calories" placeholder="1000" required value="${meal.calories}">
                        </div>
                    </form>

                    <br/>

                    <div class="col-md-5">
                        <form>
                        <button type="submit" class="btn btn-secondary" formmethod="get" formaction="${pageContext.request.contextPath}/meals">
                            <span class="fa fa-close"></span>
                            Отменить
                        </button>
                        <button type="submit" class="btn btn-primary" form="edit">
                            <span class="fa fa-check"></span>
                            Сохранить
                        </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>