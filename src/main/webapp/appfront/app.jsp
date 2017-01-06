<%--
  Created by IntelliJ IDEA.
  User: demon
  Date: 12/14/15
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
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
  <title>Paytm - {{title}}</title>
  <base href="/paytm/">
</head>

<body>
<div ng-view></div>
</body>
</html>




<%--
  Created by IntelliJ IDEA.
  User: demon
  Date: 12/14/15
  Time: 2:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%--
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
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

  <title>Paytm - {{title}}</title>
  <base href="/simex/">
</head>

<body>
<div>
  <div class="navbar">
    <div class="navbar-inner">
      <div class="navbar-container">
        <div class="navbar-header pull-left">
          <a href="#" class="navbar-brand">
            <small>
              <img src="../simex/resources/img/paytm-logo.png" alt="simex"/>
            </small>
          </a>
        </div>
        <div class="navbar-header pull-right">
          <div class="navbar-account">
            <ul class="account-area">
              <li><a href="">
                <img src="../simex/resources/img/softage_logo.png" alt="softage"
                     style="width:120px; margin-top:2px;"/>
              </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>


  <div class="login-container animated fadeInDown">

    <img src="../simex/resources/img/login_bg.png" alt=""
         style="position:absolute; top:-111px; left:50px; z-index:9999; width:200px;"/>
    <form name="myform" action="/simex/j_spring_security_check" method="post">
      <div class="loginbox bg-white" style="margin-bottom:20px;">
        <div class="loginbox-title"><i class="menu-icon fa fa-user"></i>&nbsp;Login</div>
        <label id="show1" ng-show="show1" style="color: red;margin-left: 40px;">Please check username and password</label>
        <label id="show2" ng-show="show2" style="color: red;margin-left: 40px;">Password Expired Please Reset</label>
        <div class="loginbox-textbox">
          <input id="ssoId" name="ssoId" type="text" class="form-control" ng-model="user" placeholder="Employee Code"/>
        </div>
        <div class="loginbox-textbox">
          <input id="password" name="password" type="password" class="form-control" ng-model="pass" placeholder="Password"/>
        </div>
        <div class="loginbox-forgot">
        </div>
        <div class="loginbox-submit">
          <input type="submit" class="btn btn-azure shiny btn-block" value="Login"/>
          <input ng-show="show2" type="button" class="btn btn-azure shiny btn-block" ng-click="expireOpen()" value="Reset Password"/>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

      </div>
    </form>
    <div style="height:0px; clear:both;"></div>
  </div>
</div>
</body>
</html>
--%>

