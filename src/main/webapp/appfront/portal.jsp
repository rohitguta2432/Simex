<%--
  Created by IntelliJ IDEA.
  User: demon
  Date: 12/14/15
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" ng-app="PaytmKyc">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!--Basic Styles-->
    <link href="/paytm/resources/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="/paytm/resources/css/font-awesome.min.css" rel="stylesheet"/>

    <!--Beyond styles-->
    <link href="/paytm/resources/css/beyond.min.css" rel="stylesheet"/>
    <link href="/paytm/resources/css/animate.min.css" rel="stylesheet"/>

    <link rel="stylesheet" href="/paytm/appfront/libs/html5-boilerplate/dist/css/normalize.css">
    <link rel="stylesheet" href="/paytm/appfront/libs/html5-boilerplate/dist/css/main.css">
    <link rel="stylesheet" href="/paytm/appfront/app.css">

    <script src="/paytm/appfront/libs/html5-boilerplate/dist/js/vendor/modernizr-2.8.3.min.js"></script>
    <script src="/paytm/appfront/libs/angular/angular.js"></script>
    <script src="/paytm/appfront/libs/angular-route/angular-route.js"></script>
    <script src="/paytm/appfront/app.js"></script>
    <script src="/paytm/appfront/auth/auth.js"></script>
    <script src="/paytm/appfront/components/version/version.js"></script>
    <script src="/paytm/appfront/components/version/version-directive.js"></script>
    <script src="/paytm/appfront/components/version/interpolate-filter.js"></script>

    <title>Paytm - {{title}}</title>
    <base href="/paytm/">

  </head>

  <body>
    <div ng-view></div>
  </body>
</html>