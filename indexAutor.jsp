<%-- 
    Document   : autor
    Created on : 3 mar. 2024, 15:25:15
    Author     : A5155456HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>


        <!-- Inicio para que funcione class='dropdown m-b-10' -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
        <!-- Fin para que funcione class='dropdown m-b-10' -->

        <!-- Inicio para que funcione sweetalert2@11 -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <!-- Fin para que funcione class='Mensajes sweetalert2@11 -->

        <script  src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

        <script src="http://parsleyjs.org/dist/parsley.js"></script>



        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>



    </head>

    <body class="fixed-left">

        <!-- Begin page -->
        <div id="wrapper">

            <!-- Start right Content here -->
            <div class="content-page">
                <!-- Start content -->
                <div class="content">
                    <!-- Top Bar Start -->

                    <!-- Top Bar End -->
                    <!-- ==================
                    PAGE CONTENT START
                    ================== -->
                    <div class="page-content-wrapper">
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-md-6 col-xl-6">
                                    <div class="mini-stat clearfix bg-white">
                                        <span class="mini-stat-icon bg-blue-grey mr-0 float-right"><i class="mdi mdi-black-mesa"></i></span>
                                        <div class="mini-stat-info">
                                            <span id="Autores_registradas" class="counter text-blue-grey">0</span>
                                            Autores Registrados
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>


                                <div class="col-md-6 col-xl-6" id="registrar_autor" style="cursor: pointer;">
                                    <div class="mini-stat clearfix bg-white">
                                        <span class="mini-stat-icon bg-teal mr-0 float-right"><i
                                                class="mdi mdi-account"></i></span>
                                        <div class="mini-stat-info">                                            
                                            <H3>Registrar autor</H3>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                </div>


                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div class="card m-b-20">
                                        <div class="card-body">
                                            <div id="aqui_tabla"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div><!-- container -->
                    </div> <!-- Page content Wrapper -->
                </div> <!-- content -->

            </div>
            <!-- End Right content here -->




            <div class="modal fade" id="md_registrar_autor" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <sub>Campos marcados con * son obligatorios</sub>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        
                        
                        <div class="modal-body">
                            <form name="formulario_registro" id="formulario_registro">
                                <input type="hidden" id="llave_persona" name="llave_persona" value="">
                                <input type="hidden" id="consultar_datos" name="consultar_datos" value="si_registro">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Codigo *</label>
                                            <input type="text" autocomplete="off" name="codigoautor" maxlength="5"
                                                   data-parsley-error-message="Campo requerido" id="codigoautor"
                                                   class="form-control"
                                                   required placeholder="Ingrese el codigo"/>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Nombre</label>
                                            <input type="text" autocomplete="off" name="nombreautor" maxlength="60"
                                                   data-parsley-error-message="Campo requerido" id="nombreautor"
                                                   class="form-control"
                                                   required placeholder="Ingrese el Nombre"/>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Apellidos</label>
                                            <input type="text" autocomplete="off" name="apellidoautor" maxlength="35"
                                                   data-parsley-error-message="Campo requerido" id="apellidoautor"
                                                   class="form-control"
                                                   required placeholder="Ingrese el Apellido "/>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Fecha Nacimiento</label>
                                            <input type="date" autocomplete="off" name="fechanacimiento"
                                                   data-parsley-error-message="Campo requerido" id="fechanacimiento"
                                                   class="form-control"
                                                   required placeholder="Ingrese Fecha de Nacimiento"/>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Nacionalidad</label>
                                            <input type="text" autocomplete="off" name="nacionalidad" maxlength="40"
                                                   data-parsley-error-message="Campo requerido" id="nacionalidad"
                                                   class="form-control"
                                                   required placeholder="Ingrese la nacionalidad "/>
                                        </div>
                                    </div>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" id="btn_cerrar" class="btn btn-secondary " data-dismiss="modal">Cerrar</button>
                                    <button type="submit" class="btn btn-primary ">Guardar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div> 
    </body>
    <script src="funciones_autor.js"></script>
</html>
