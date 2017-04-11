<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>



  <meta charset="utf-8" />
  <title>Simex Dashboard</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <sec:csrfMetaTags/>
  <!--Basic Styles-->
  <link href="libs/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" />
  <link href="libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
    <link href="libs/angular-material/angular-material.min.css" rel="stylesheet" />
    <script src="libs/jquery/dist/jquery.min.js"></script>
    <script src="libs/jquery/dist/jquery-ui.min.js"></script>
    <script src="libs/angular/angular.min.js"></script>
    <script src="libs/angular-animate/angular-animate.min.js"></script>
    <script src="libs/angular-aria/angular-aria.min.js"></script>
    <script src="libs/angular-messages/angular-messages.min.js"></script>
    <script src="libs/angular-material/angular-material.min.js"></script>
  <%--<script src="http://code.angularjs.org/1.2.13/angular.js"></script>--%>

   <%-- <link href="libs/datepicker/jquery-ui.css" rel="stylesheet" />--%>
    <%--<script type="text/javascript" src="libs/angular/angular.min.js"></script>
    <script type="text/javascript" src="libs/jquery/dist/jquery-ui.min.js"></script>
    <script type="text/javascript" src="libs/jquery/dist/jquery.min.js"></script>--%>

   <%-- <script src="libs/angular/angular.min.js"></script>--%>
    <%--<link href="libs/jqueryui/jquery-ui.css" rel="stylesheet" />--%>
    <link href="libs/jquery-ui/themes/smoothness/jquery-ui.css" rel="stylesheet" />
    <%--<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.0/angular.min.js"></script>--%>
    <%--<link rel="stylesheet" type="text/css" href="libs/jqueryui/jquery-ui.css">--%>


    <!--Loader -->
    <script src="libs/spin.js/spin.js"></script>
    <script src="libs/angular-spinner/angular-spinner.min.js"></script>
    <script src="libs/angular-loading-spinner/angular-loading-spinner.js"></script>


  <%--<script src="//cdnjs.cloudflare.com/ajax/libs/angular-ui-router/0.2.8/angular-ui-router.min.js"></script>--%>
  <script src="libs/angular-ui-router/release/angular-ui-router.min.js"></script>
  <script src="libs/filesaver/FileSaver.js"></script>
  <script src="js/app.js"></script>
    <script src="Manual/manual.js"></script>
    <script src="Manual/manualFilter.js"></script>
  <!--Beyond styles-->
  <link href="assets/css/beyond.min.css" rel="stylesheet" type="text/css" />
  <link href="assets/css/animate.min.css" rel="stylesheet" />
<%--    <link href="assets/css/angularjs-datetime-picker.css" rel="stylesheet" />--%>
  <script src="assets/js/skins.min.js"></script>
<%--    <script src="assets/js/angularjs-datetime-picker.js"></script>--%>

    <%--<style>
        input.ng-invalid{border-color:red;}
        select.ng-invalid{border-color:red;}
    </style>--%>


  <style type="text/css">
    form.ng-submitted input.ng-invalid {
      background: red;
    }
    form.ng-submitted select.ng-invalid{
      background: red;
    }

    .radioButtondemoBasicUsage body {
        padding: 20px; }
    .radioButtondemoBasicUsage hr {
        margin-left: -20px;
        opacity: 0.3; }
    .radioButtondemoBasicUsage md-radio-group {
        width: 150px; }
    .radioButtondemoBasicUsage p:last-child {
        padding-bottom: 50px; }
    .radioButtondemoBasicUsage [ng-controller] {
        padding: 0 20px; }
    .radioButtondemoBasicUsage .radioValue {
        margin-left: 5px;
        color: #0f9d58;
        font-weight: bold;
        padding: 5px; }
    .radioButtondemoBasicUsage md-icon {
        margin: 0 20px 20px;
        width: 128px;
        height: 128px; }
    .radioButtondemoBasicUsage .ipsum {
        color: saddlebrown;
        font-size: 0.9em; }


  </style>

</head>
<!-- /Head -->
<!-- Body -->
<body ng-app="routerApp">

<%
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Expires", "0");
    response.setDateHeader("Expires", -1);

    String rolename="";
    String name=(String)session.getAttribute("name");
    if(name==null) {

        response.sendRedirect("/simex/#/auth");
    }

    String role=(String)session.getAttribute("role");


    if ("ADM".equalsIgnoreCase(role)){
        rolename="Admin";
    }
    if ("HR".equalsIgnoreCase(role)){
        rolename="HR";
    }
    if ("A1".equalsIgnoreCase(role)){
        rolename="Agent";
    }
    if ("AOQC".equalsIgnoreCase(role)){
        rolename="AO_Audit";
    }
    if ("CAQC".equalsIgnoreCase(role)){
        rolename="CircleAudit";
    }
    if ("DULD".equalsIgnoreCase(role)){
        rolename="Client User";
    }
  if(name==null){
%>
   <jsp:forward page=""/>
<%
  }

%>

<!-- Navbar -->
<div class="navbar">
  <div class="navbar-inner">
    <div class="navbar-container">
      <!-- Navbar Barnd -->
      <div class="navbar-header pull-left">
        <a href="#" class="navbar-brand"><small>
          <img src="assets/img/simex-logo.png" alt="simex"  />
        </small></a>
      </div>
      <!-- /Navbar Barnd -->
      <!-- Sidebar Collapse -->
      <div class="sidebar-collapse" id="sidebar-collapse">
        <i class="collapse-icon fa fa-bars"></i>
      </div>
      <!-- /Sidebar Collapse -->
      <!-- Account Area and Settings --->
      <div class="navbar-header pull-right" ng-controller="logout">
        <div class="navbar-account">
          <ul class="account-area">
            <li>
              <a class="login-area dropdown-toggle" data-toggle="dropdown">
                <div class="avatar" title="View your public profile">
                  <img src="assets/img/avatars/usericon.png">
                </div>
                <section>
                  <h2>
                                        <span class="profile"><span><%=rolename%> Login </span>
                                        </span>
                  </h2>
                </section>
              </a>
              <!--Login Area Dropdown-->
              <ul class=" dropdown-menu dropdown-arrow dropdown-login-area">
                <%--<li class="dropdown-footer pull-left"><i class="icon fa fa-sign-out"></i><a href="login.html">
                  &nbsp;&nbsp;Sign out </a></li>--%>
                    <li><a ng-click="logout()"><i class="menu-icon fa fa-sign-out"></i><span style="cursor: pointer;" class="menu-text">
                        &nbsp;&nbsp;Sign out</span> </a></li>
              </ul>
              <!--Login Area Dropdown-->
            </li>
          </ul>

        </div>
      </div>
      <!-- Account Area -->
    </div>
  </div>
</div>
<!-- /Navbar -->
<!-- Main Container -->
<div class="main-container container-fluid">
  <!-- Page Container -->
  <div class="page-container">
    <!-- Page Sidebar -->
    <div class="page-sidebar" id="sidebar" ng-controller="logout">
      <!-- Sidebar Menu -->
      <ul class="nav sidebar-menu">
        <%--<li class="active"><a ui-sref="index"><i class="menu-icon fa fa-home"></i><span
                class="menu-text">Dashboard</span></a></li>--%>
            <%
                    if("ADM".equalsIgnoreCase(role))
                    {

            %>
        <li><a ui-sref="registration"><i class="menu-icon fa fa-user"></i><span class="menu-text">
                       Agent Registration </span></a>
        </li>

            <%--<li><a ui-sref="manual"><i class="menu-icon fa fa-user"></i><span class="menu-text">
                        Manual Lead Allocation </span></a>
            </li>--%>

         <li><a ui-sref="uploadAgent"><i class="menu-icon fa fa-upload"></i><span class="menu-text">
                        Agent Upload </span></a></li>

        <li><a ui-sref="uploadScreen"><i class="menu-icon fa fa-upload"></i><span class="menu-text">
                        Import Data </span></a></li>

        <li><a ui-sref="telecalling"><i class="menu-icon fa fa-phone"></i><span class="menu-text">
                        Telecalling </span></a></li>
            <li><a ui-sref="HRRegistration"><i class="menu-icon fa fa-group"></i><span class="menu-text">
                        Registration</span></a></li>

       <li><a ui-sref="report"><i class="menu-icon fa fa-calendar"></i><span class="menu-text">
                        Reports </span></a></li>
            <li><a ui-sref="CircleAudit"><i class="menu-icon fa fa-calendar"></i><span class="menu-text">
                           Circle Audit </span></a></li>

        <li><a ng-click="logout()"><i class="menu-icon fa fa-sign-out"></i><span style="cursor: pointer;" class="menu-text">
                        Sign out</span> </a></li>
        <%

          }
        %>


          <%
            if("HR".equalsIgnoreCase(role))
            {

          %>
          <li><a ui-sref="registration"><i class="menu-icon fa fa-user"></i><span class="menu-text">
                       Agent Registration </span></a>
          </li>

          <li><a ng-click="logout()"><i class="menu-icon fa fa-sign-out"></i><span style="cursor: pointer;" class="menu-text">
                        Sign out</span> </a></li>
          <%

            }
          %>
            <%
                if("CAQC".equalsIgnoreCase(role))
                {

            %>
            <%--<li><a ui-sref="registration"><i class="menu-icon fa fa-user"></i><span class="menu-text">
                       Agent Registration </span></a>
            </li>--%>
            <li><a ui-sref="CircleAudit"><i class="menu-icon fa fa-calendar"></i><span class="menu-text">
                           Circle Audit </span></a></li>
            <li><a ng-click="logout()"><i class="menu-icon fa fa-sign-out"></i><span style="cursor: pointer;" class="menu-text">
                        Sign out</span> </a></li>
            <%-- <li><a ui-sref="dataentry"><i class="menu-icon fa fa-edit"></i><span class="menu-text">
                             Data Entry </span></a></li>--%>
            <%--<li><a ng-click="logout()"><i class="menu-icon fa fa-sign-out"></i><span style="cursor: pointer;" class="menu-text">
                        Sign out</span> </a></li>--%>
            <%

                }
            %>
            <%
                if("AOQC".equalsIgnoreCase(role))
                {

            %>
            <%--<li><a ui-sref="registration"><i class="menu-icon fa fa-user"></i><span class="menu-text">
                       Agent Registration </span></a>
            </li>--%>
            <li><a ui-sref="AoAudit"><i class="menu-icon fa fa-calendar"></i><span class="menu-text">
                           AO Audit </span></a></li>
            <li><a ui-sref="FormRecieving"><i class="menu-icon fa fa-calendar"></i><span class="menu-text">
                           Form Recieving </span></a></li>
            <li><a ng-click="logout()"><i class="menu-icon fa fa-sign-out"></i><span style="cursor: pointer;" class="menu-text">
                        Sign out</span> </a></li>
            <%-- <li><a ui-sref="dataentry"><i class="menu-icon fa fa-edit"></i><span class="menu-text">
                             Data Entry </span></a></li>--%>
            <%--<li><a ng-click="logout()"><i class="menu-icon fa fa-sign-out"></i><span style="cursor: pointer;" class="menu-text">
                        Sign out</span> </a></li>--%>
            <%

                }
            %>

          <%
            if("Tlec".equalsIgnoreCase(role))
            {

          %>
          <li><a ui-sref="telecalling"><i class="menu-icon fa fa-phone"></i><span class="menu-text">
                        Telecalling </span></a></li>
          <%-- <li><a ui-sref="dataentry"><i class="menu-icon fa fa-edit"></i><span class="menu-text">
                           Data Entry </span></a></li>--%>
          <li><a ng-click="logout()"><i class="menu-icon fa fa-sign-out"></i><span style="cursor: pointer;" class="menu-text">
                        Sign out</span> </a></li>

            <%

            }
          %>

            <%
                if("DULD".equalsIgnoreCase(role))
                {

            %>
            <li><a ui-sref="uploadScreen"><i class="menu-icon fa fa-upload"></i><span class="menu-text">
                        Import Data </span></a></li>
            <%-- <li><a ui-sref="dataentry"><i class="menu-icon fa fa-edit"></i><span class="menu-text">
                             Data Entry </span></a></li>--%>
            <li><a ng-click="logout()"><i class="menu-icon fa fa-sign-out"></i><span style="cursor: pointer;" class="menu-text">
                        Sign out</span> </a></li>
            <%

                }
            %>

      </ul>
      <!-- /Sidebar Menu -->
    </div>
    <!-- /Page Sidebar -->
    <!-- Page Content -->
    <div class="page-content">
      <!-- Page Breadcrumb -->
      <div class="page-breadcrumbs">
       <%-- <ul class="breadcrumb">
          <li><i class="fa fa-home"></i><a href="home.html">Home</a> </li>
          <li class="active">Dashboard</li>
        </ul>--%>
      </div>
      <!-- /Page Breadcrumb -->

      <!-- Page Body -->
      <div class="page-body">
        <!-- <div class="row">
            <div class="col-md-12 col-sm-12 col-xs-12" style="text-align:center;">
                <img src="assets/img/home.png" alt="" style="margin-top:5%;" />
            </div>
        </div>-->
        <div ui-view></div>
      </div>
      <!-- /Page Body -->
    </div>
    <!-- /Page Content -->
  </div>
  <!-- /Page Container -->
  <!-- Main Container -->
</div>

<footer style="background: #fff;">
  <div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="border-top:1px solid #ddd; text-align:center; padding-top:5px;">
      Copyright &copy; 2017,  <img src="assets/img/softage_logo.png" alt="softage" style="margin-top: -1px; height:20px;" />
    </div>
  </div>
</footer>


<!--Basic Scripts-->
<script src="assets/js/jquery-2.0.3.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/jquery.slimscroll.min.js"></script>
<!--Beyond Scripts-->
<script src="assets/js/beyond.js"></script>
<script src="assets/js/datetime-picker.js"></script>
<script src="assets/js/index.js"></script>
<script src="assets/js/ui-bootstrap-tpls.js"></script>
<script src="assets/js/ui-bootstrap-tpls-0.14.3.min.js"></script>
<%--<script src="assets/js/ngDialog.js"></script>--%>



</body>
<!--/Body -->
</html>
