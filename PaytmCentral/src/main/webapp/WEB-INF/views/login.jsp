<!DOCTYPE html>
<html>
<!--Head-->
<head>
    <meta charset="utf-8"/>
    <title>Login Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <!--Basic Styles-->
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="../../resources/css/font-awesome.min.css" rel="stylesheet"/>

    <!--Beyond styles-->
    <link href="../../resources/css/beyond.min.css" rel="stylesheet"/>
    <link href="../../resources/css/animate.min.css" rel="stylesheet"/>

</head>

<body>
<div class="navbar">
    <div class="navbar-inner">
        <div class="navbar-container">
            <div class="navbar-header pull-left">
                <a href="#" class="navbar-brand">
                    <small>
                        <img src="../../resources/img/paytm-logo.png" alt="paytm"/>
                    </small>
                </a>
            </div>
            <div class="navbar-header pull-right">
                <div class="navbar-account">
                    <ul class="account-area">
                        <li><a href="">
                            <img src="../../resources/img/softage_logo.png" alt="softage"
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

    <img src="../../resources/img/login_bg.png" alt=""
         style="position:absolute; top:-111px; left:50px; z-index:9999; width:200px;"/>

    <div class="loginbox bg-white" style="margin-bottom:20px;">
        <div class="loginbox-title"><i class="menu-icon fa fa-user"></i> Login For Paytm Customers</div>
        <div class="loginbox-textbox">
            <input type="text" class="form-control" placeholder="Mobile No"/>
        </div>
        <div class="loginbox-textbox">
            <input type="text" class="form-control" placeholder="OTP"/>
        </div>
        <div class="loginbox-forgot">
        </div>
        <div class="loginbox-submit">
            <input type="button" class="btn btn-azure shiny btn-block" onclick="location.href='home.html'"
                   value="Login"/>
        </div>
    </div>
    <div style="height:0px; clear:both;"></div>
</div>


<footer>
    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"
             style="border-top:1px solid #ddd; text-align:center; padding-top:5px;">
            Copyright &copy; 2015, SoftAge Information Technology Limited
        </div>
    </div>
</footer>



<script src="../../resources/js/jquery-2.0.3.min.js"></script>
<script src="../../resources/js/bootstrap.min.js"></script>
<script src="../../resources/js/jquery.slimscroll.min.js" />
<script src="../../resources/js/beyond.js"></script>


</body>
</html>
 