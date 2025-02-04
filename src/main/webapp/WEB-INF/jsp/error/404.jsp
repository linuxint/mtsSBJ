<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title><s:message code="common.pageTitle"/></title>

    <link href="css/sb-admin/bootstrap.min.css" rel="stylesheet">

    <style type="text/css">
        .error-template {
            padding: 40px 15px;
            text-align: center;
        }

    </style>
</head>

<body>

<div class="wrapper">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1>
                    Oops!</h1>
                <h2>404 Not Found</h2>
                <div class="error-details">
                    Sorry, an error has occured, Requested page not found!
                </div>
                <div>
                    <a href="index" class="btn btn-default btn-lg">
                        <span class="glyphicon glyphicon-home"></span>Take Me Home </a>
                </div>
                <div layout:fragment="content">
                    <h1>Error Page</h1>
                    error code : ${status}
                    <br>error : ${error}
                    <br>message : ${message}
                    <br>timestamp : ${timestamp}
                    <br>path : ${path}
                </div>
            </div>
        </div>
    </div>
</div>


</body>

</html>
