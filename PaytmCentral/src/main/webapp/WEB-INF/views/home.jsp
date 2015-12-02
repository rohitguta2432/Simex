<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
    <meta charset="utf-8" />
    <title>Login Page</title>

   

    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


    <!--Basic Styles-->
    <link href="~/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link href="~/assets/css/font-awesome.min.css" rel="stylesheet" />

    <!--Beyond styles-->
    <link href="~/assets/css/beyond.min.css" rel="stylesheet" />
    @*<link href="~/assets/css/demo.min.css" rel="stylesheet" />*@
    <link href="~/assets/css/animate.min.css" rel="stylesheet" />


    <script src="~/Scripts/jquery.validate.min.js"></script>
    <script src="~/Scripts/jquery-1.7.1.min.js"></script>
    <script src="~/js/CustomerValidation.js"></script>
    <script src="~/js/AgentEntryValidate.js"></script>



    <style>
        .textbox {
            background-color: red !important;
        }
    </style>


    <script type="text/javascript">



        function OTP() {

            if ($("#mobilenumber").val() == '') {
                alert("Please enter Mobile Nummber");
                $("#mobilenumber").focus();
            }
            else {

                if (ValidateMobile($("#mobilenumber").val()) == true) {
                    $.ajax
                  ({

                      type: "POST",
                      url: '@Url.Action("SendOTP", "Home")',
                      data: { "mobile": $("#mobilenumber").val() },
                      async: false,
                      datatype: "text",
                      success: function (data) {
                          if (data == 1) {
                              alert("Thanks you will receive OTP");
                              $("#mobilenumber").val('');
                          }
                      }
                  });

                }
                else {
                    alert("PLease check Mobile number");
                    $("#mobilenumber").focus();
                }

                }
                }


$(function () {




    $("input[type='text']").bind('keypress', function (event) {

        // start change

        var kc = event.which;


        //alert(kc);

        if (event.keyCode == 9 && event.shiftKey) {

            event.preventDefault();

        }

        else if (event.shiftKey)

            event.preventDefault();


        if ((kc >= 65 && kc <= 90) || (kc == 46 || kc == 8 || kc == 9 || kc == 0) || (kc >= 48 && kc <= 57) || (kc >= 96 && kc <= 122) || (kc == 37 || kc == 32 || kc == 39))

        { }

        else {

            event.preventDefault();

            return false;

        }

    });









    $("#employeelogin").click(function (e) {


        var emp = $("#employeeform").find(".required");
        var validate = true;
        emp.each(function () {

            if ($.trim($(this).val()) == '') {
                $(this).addClass("textbox");

                validate = false;
                e.preventDefault();
            }
            else {

                validate = true;

            }

            if ($(this).keypress(function () {
                $(this).removeClass("textbox");
            }));

        });
        //Textbox Empty Validation Ends


        if (validate == true) {

            $("#employeeform").submit();

        }
        else {
            e.preventDefault();
        }



    });

    $("#cuslogin").click(function (e) {

        var required = $("#CustomerForm").find(".required");
        var validatecustomer = true;
        required.each(function () {

            if ($.trim($(this).val()) == '') {
                $(this).addClass("textbox");
                e.preventDefault();
                validatecustomer = false;
            }

            if ($(this).keypress(function () {
                 $(this).removeClass("textbox");
                validatecustomer = true;
            }));

        });


        if (validatecustomer == true) {

            var mobilenumber = $("#mobilenumber").val();
            var cust = true;
            if (CustomerMobileVali(mobilenumber) == true) {
                cust = true;
            }

            else {
                $("#mobilenumber").addClass("textbox");
                e.preventDefault();
                cust = false;
            }

            if (cust == true) {
                $("#CustomerForm").submit();
            }
        }

    });


});

    </script>

</head>


<body>

    <!-- Navbar -->
    <div class="navbar">
        <div class="navbar-inner">
            <div class="navbar-container">
                <!-- Navbar Barnd -->
                <div class="navbar-header pull-left">
                    <a href="#" class="navbar-brand"><small>
                        <img src="~/assets/img/paytm-logo.png" alt="paytm" />
                    </small></a>
                </div>
                <!-- /Navbar Barnd -->

               <div class="pull-right">
                    <img src="~/assets/img/softage_logo.png" alt="softage" style="margin:10px 20px 0 0; width:120px;" />

                </div>

            </div>
        </div>
    </div>
    <!-- /Navbar -->

    <div class="login-container animated fadeInDown">




        <div class="loginbox bg-white" style="margin:0 auto;">
            <form id="employeeform" action="~/Home/EmployeeLogin" method="post">
                <div class="loginbox-title"><i class="menu-icon fa fa-user"></i>Softage KYC Management System</div>


                <div style="clear: both; height: 10px; text-align: center;">

                    @if (ViewBag.values != "")
                    {
                        <label id="show1" style="color: red;">@ViewBag.values</label>
                    }
                    else
                    {

                    }

                </div>

                <div class="loginbox-textbox">
                    <input type="text" id="empcode" name="empcode" maxlength="6" placeholder="Employee Code" class="form-control required" required autofocus />

                </div>

                <div class="loginbox-textbox">
                    <input type="password" id="emppass" name="emppass" maxlength="10" class="form-control required" placeholder="Password" />

                </div>

                <div class="loginbox-forgot">
                    <!--<a href="">Forgot Password?</a>-->
                </div>
                <div class="loginbox-submit">

                    <input type="submit" value="Login" id="employeelogin" class="btn btn-azure shiny btn-block" />





                </div>
                <div style="height: 5px; clear: both;"></div>

            </form>


        </div>

        <!--Employee Login Ends-->






       @* <div class="loginbox bg-white" style="float: left; margin-bottom: 20px;">
            <form id="CustomerForm" method="post" action="~/Home/CustomerLogin">

                <div class="loginbox-title"><i class="menu-icon fa fa-user"></i>PayTm Customer Login</div>

                <div style="clear: both; height: 10px; text-align: center;">

                    @if (ViewBag.value != "")
                    {
                        <label id="show" style="color: red;">@ViewBag.value</label>
                    }
                    else
                    {

                    }


                </div>

                <div class="loginbox-textbox">
                    <input type="text" id="mobilenumber" name="mobilenumber" maxlength="10" placeholder="Mobile Number" class="form-control required" />

                </div>
                <div class="loginbox-textbox">

                    <input type="password" class="form-control required" id="OTP" maxlength="5" name="OTP" placeholder="OTP" />
                </div>
                <div class="loginbox-forgot">
                    <!--<a href="">Forgot Password?</a>-->
                </div>
                <div class="loginbox-submit">

                    <input type="submit" value="Customer Login" id="cuslogin" class="btn btn-azure shiny btn-block" />



                </div>
                <div style="height: 5px; clear: both; text-align: center;">
                    <p id="otp" onclick="OTP()" style="cursor:pointer; margin:0; padding:0;">Click here to Genreate OTP or SMS OTP on 8010331033</p>
                </div>
            </form>*@

        </div>





        <!--PayTm Login Ends-->

    </div>

    <!--Container Ends here-->
    <div style="height: 15px; clear: both;"></div>

    <footer>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="border-top: 1px solid #ddd; text-align: center; padding-top: 5px;">
                Copyright &copy; 2015, SoftAge Information Technology Limited @*<a href="http://www.softage.net" target="_blank">
                    <img src="~/assets/img/softage_logo.png" alt="softage" style="margin-top: -1px; height: 20px;" />
                </a>*@
            </div>

        </div>
    </footer>

</body>
</html>


<%-- <html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
</body>
</html> --%>
