<%--
  Created by IntelliJ IDEA.
  User: SS0085
  Date: 21-11-2016
  Time: 06:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" ng-app="PaytmAuth">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!--Basic Styles-->
  <link href="/simex/resources/css/bootstrap.min.css" rel="stylesheet"/>
  <link href="/simex/resources/css/font-awesome.min.css" rel="stylesheet"/>

  <!--Beyond styles-->
  <link href="/simex/resources/css/beyond.min.css" rel="stylesheet"/>
  <link href="/simex/resources/css/animate.min.css" rel="stylesheet"/>

  <link rel="stylesheet" href="/simex/appfront/libs/html5-boilerplate/dist/css/normalize.css">
  <link rel="stylesheet" href="/simex/appfront/libs/html5-boilerplate/dist/css/main.css">
  <link rel="stylesheet" href="/simex/appfront/libs/app.css">

  <script src="/simex/appfront/libs/html5-boilerplate/dist/js/vendor/modernizr-2.8.3.min.js"></script>
  <script src="/simex/appfront/libs/angular/angular.js"></script>
  <script src="/simex/appfront/libs/angular-route/angular-route.js"></script>
  <script src="/simex/appfront/app.js"></script>
  <script src="/simex/appfront/auth/auth.js"></script>
  <script src="/simex/appfront/components/version/version.js"></script>
  <script src="/simex/appfront/components/version/version-directive.js"></script>
  <script src="/simex/appfront/components/version/interpolate-filter.js"></script>
</head>

<body>

<%
   String user1 = request.getParameter("user");
   System.out.println("user  "+user1);
%>

</body>


<div class="login-container animated fadeInDown">
  <h1>Reset Password</h1>
  <form name="myform" ng-controller="AuthCtrl" ng-submit="expire()" method="get" novalidate>
    <div class="loginbox bg-white" style="margin-bottom:20px;">
      <%--<label id="show3" ng-show="show3" style="color: red;margin-left: 40px;">Password mismatch please enter again</label>--%>
      <div class="loginbox-textbox">
        <input type="password" minlength="9" maxlength="15"  class="form-control" ng-model="pass" placeholder="Password" required />
      </div>
      <div class="loginbox-textbox" >
        <input type="hidden" class="form-control" ng-init="user= '<%= user1 %>'"/>
      </div>
      <div class="loginbox-textbox">
        <input type="password" minlength="9" maxlength="15" class="form-control" ng-model="pass1" placeholder="Confirm Password" required/>
      </div>
      <div class="loginbox-forgot">
      </div>
      <div class="loginbox-submit">
        <input type="submit" class="btn btn-azure shiny btn-block"  value="Submit"/>
      </div>


    </div>
  </form>
  <div style="height:0px; clear:both;"></div>
</div>
</html>
