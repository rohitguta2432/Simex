
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
  <meta charset="utf-8" />
  <title>Paytm Dashboard</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <!--Basic Styles-->
  <link href="libs/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" />
  <link href="libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
  <%--<script src="http://code.angularjs.org/1.2.13/angular.js"></script>--%>
  <script src="libs/angular/angular.js"></script>
  <%--<script src="//cdnjs.cloudflare.com/ajax/libs/angular-ui-router/0.2.8/angular-ui-router.min.js"></script>--%>
  <script src="libs/angular-ui-router/release/angular-ui-router.min.js"></script>
  <script src="js/app.js"></script>
  <!--Beyond styles-->
  <link href="assets/css/beyond.min.css" rel="stylesheet" type="text/css" />
  <link href="assets/css/animate.min.css" rel="stylesheet" />
  <script src="assets/js/skins.min.js"></script>

</head>
<!-- /Head -->
<!-- Body -->
<body ng-app="routerApp">
<!-- Navbar -->
<div class="navbar">
  <div class="navbar-inner">
    <div class="navbar-container">
      <!-- Navbar Barnd -->
      <div class="navbar-header pull-left">
        <a href="#" class="navbar-brand"><small>
          <img src="assets/img/paytm-logo.png" alt="paytm"  />
        </small></a>
      </div>
      <!-- /Navbar Barnd -->
      <!-- Sidebar Collapse -->
      <div class="sidebar-collapse" id="sidebar-collapse">
        <i class="collapse-icon fa fa-bars"></i>
      </div>
      <!-- /Sidebar Collapse -->
      <!-- Account Area and Settings --->
      <div class="navbar-header pull-right">
        <div class="navbar-account">
          <ul class="account-area">
            <li>
              <a class="login-area dropdown-toggle" data-toggle="dropdown">
                <div class="avatar" title="View your public profile">
                  <img src="assets/img/avatars/usericon.png">
                </div>
                <section>
                  <h2>
                                        <span class="profile"><span>Admin Login </span>
                                        </span>
                  </h2>
                </section>
              </a>
              <!--Login Area Dropdown-->
              <ul class=" dropdown-menu dropdown-arrow dropdown-login-area">
                <li class="dropdown-footer pull-left"><i class="icon fa fa-sign-out"></i><a href="login.html">
                  &nbsp;&nbsp;Sign out </a></li>
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
    <div class="page-sidebar" id="sidebar">
      <!-- Sidebar Menu -->
      <ul class="nav sidebar-menu">
        <li class="active"><a ui-sref="index"><i class="menu-icon fa fa-home"></i><span
                class="menu-text">Dashboard</span></a></li>

        <li><a ui-sref="registration"><i class="menu-icon fa fa-user"></i><span class="menu-text">
                       Agent Regsitration </span></a>
        </li>
        <li><a ui-sref="dataentry"><i class="menu-icon fa fa-edit"></i><span class="menu-text">
                        Data Entry </span></a></li>
        <li><a ui-sref="uploadScreen"><i class="menu-icon fa fa-upload"></i><span class="menu-text">
                        Upload Data Screen </span></a></li>
        <li><a ui-sref="telecalling"><i class="menu-icon fa fa-phone"></i><span class="menu-text">
                        Telecalling </span></a></li>
        <li><a ui-sref="registration"><i class="menu-icon fa fa-calendar"></i><span class="menu-text">
                        Reports </span></a></li>
        <li><a ui-sref="registration"><i class="menu-icon fa fa-sign-out"></i><span class="menu-text">
                        Sign out</span> </a></li>
      </ul>
      <!-- /Sidebar Menu -->
    </div>
    <!-- /Page Sidebar -->
    <!-- Page Content -->
    <div class="page-content">
      <!-- Page Breadcrumb -->
      <div class="page-breadcrumbs">
        <ul class="breadcrumb">
          <li><i class="fa fa-home"></i><a href="home.html">Home</a> </li>
          <li class="active">Dashboard</li>
        </ul>
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

<footer>
  <div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="border-top:1px solid #ddd; text-align:center; padding-top:5px;">
      Copyright &copy; 2015,  <img src="assets/img/softage_logo.png" alt="softage" style="margin-top: -1px; height:20px;" />
    </div>
  </div>
</footer>


<!--Basic Scripts-->
<script src="assets/js/jquery-2.0.3.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/jquery.slimscroll.min.js"></script>
<!--Beyond Scripts-->
<script src="assets/js/beyond.js"></script>

</body>
<!--/Body -->
</html>
