$(function () {
    //VALIDANDO DATOS DEL FORMULARIO /.parsley)
    $('#formulario_registro').parsley();
    cargarTabla();
    $(document).on("click", "#registrar_autor", function (e) {
        e.preventDefault();
        $("#formulario_registro").trigger("reset");
        $("#md_registrar_autor").modal("show");        
        document.querySelector('#codigoautor').readOnly = false;
    });


//Se ejecuta cuando se presiona clic en el boton Guardar del formulario_registro
// Ya sea inserción o modificación de datos
   //  <form name="formulario_registro" id="formulario_registro"> desde jsp
    
    $(document).on("submit", "#formulario_registro", function (e) {
        e.preventDefault();
        mostrar_cargando("Procesando Solicitud", "Espere mientras se almacenan los datos");
        var datos = $("#formulario_registro").serialize();  
        
        console.log("Datos capturados en el formulario a insertar o modificar "+datos);
        $.ajax({
            dataType: "json",
            method: "POST",
            url: "RegAutor",
            data: datos
        }).done(function (json) {
            Swal.close();
            if (json[0].resultado === "exito") { // así se accede a un arreglo
                Swal.fire('Exito', 'Autor Registrado', 'success');
                $("#md_registrar_autor").modal("hide");
                cargarTabla();
                location.reload();// para actualizar la página 
            } else {
                Swal.fire('Accion no completada', "No se puede registrar el Autor", "Error");
            }
        }).fail(function () {
        }).always(function () {
        });
    });
});


$(document).on("click", ".btn_editar", function (e) {
    e.preventDefault();
    mostrar_cargando("Espere", "Obteniendo datos");
   
    var id = $(this).attr("data-id");
    
    // html += "<a class='dropdown-item btn_editar' data-id='" + objAutor.getCodigoAutor() +"'>Editar</a>";
    console.log("data-id" + id);
    
    //  String filtro = req.getParameter("consultar_datos"); (está en el controlador)
    var datos = {"consultar_datos": "si_autor_especifico", "id": id};
    console.log("datos enviados al controlador desde el botón editar" + datos);
    $.ajax({
        dataType: "json",
        method: "POST",
        url: "RegAutor",
        data: datos
    }).done(function (json) {  
        console.log("datos del json que vienen desde el controlador cuando el caso si_autor_especifico "+json);
            // .done Esta parte se realiza si desde el controlador se obtiene éxito
            // Revisar el case de "si_autor_especifico"            
                    //json_especifico.put("resultado", "exito");
                   // json_especifico.put("id_persona", res_indiv.getCodigoAutor());
                   // json_especifico.put("codigoautor", res_indiv.getCodigoAutor());
                   // json_especifico.put("nombreautor", res_indiv.getNombreAutor());
                    //json_especifico.put("apellidoautor", res_indiv.getApellidoAutor());
                    //json_especifico.put("fechanacimiento", res_indiv.getFechaNacimiento());
                    //json_especifico.put("nacionalidad", res_indiv.getNacionalidad());
                    
            if (json[0].resultado === "exito") {
            $("#formulario_registro").trigger("reset");
            $("#consultar_datos").val("si_actualizalo"); // asignando si_actualizalo a consultar_datos para que actualice el registro
            $('#llave_persona').val(json[0].id_persona); // obteniendo los datos del arreglo json que se recibe en la línea 57
            $('#codigoautor').val(json[0].codigoautor); // obteniendo datos del arreglo json
            $('#nombreautor').val(json[0].nombreautor); // obteniendo datos del arreglo json
            $('#apellidoautor').val(json[0].apellidoautor); // obteniendo datos del arreglo json
            $('#fechanacimiento').val(json[0].fechanacimiento); // obteniendo datos del arreglo json
            $('#nacionalidad').val(json[0].nacionalidad); // obteniendo datos del arreglo json
            document.querySelector('#codigoautor').readOnly = true; // el codigo del autor no se debe modificar
               // en la jsp
               // <div class="modal fade" id="md_registrar_autor" 
               // tabindex="-1" role="dialog"
               //  aria-labelledby="exampleModalLabel"
               //  aria-hidden="true">
                 
            $("#md_registrar_autor").modal("show");  // se muestra el modal de md_registrar_autor
        }
    }).fail(function () {
    }).always(function () {
    });
});


$(document).on("click", ".btn_eliminar", function (e) {
    e.preventDefault();
    Swal.fire({
        title: '¿Desea eliminar el registro?',
        text: 'Al continuar, no podrá ser revertido y los datos serán borrados completamente',
        showDenyButton: true,
        showCancelButton: false,
        confirmButton: 'si',
        denyButton: 'NO'
    }).then((result) => {
        if (result.isConfirmed) {
              //html += "<a class='dropdown-item btn_eliminar' data-id='" 
              // + objAutor.getCodigoAutor() +"'>Eliminar</a> ";
                   
            eliminar($(this).attr('data-id'));
            console.log(" registro a eliminar:  data-id" + $(this).attr('data-id'));
            
        } else if (result.isDenied) {
            Swal.fire("Opcion cancelada por el usuario", '', 'info');
        }
    });
});

function cargarTabla(estado = 1) {
    mostrar_cargando("procesando solicitud", "Espere mientas se procesa la información");
    var datos = {"consultar_datos": "si_consulta", "estado": estado};
    console.log("esta aqui");
    console.log("datos"+ datos.consultar_datos);
    console.log("datos"+ datos.estado);
    $.ajax({
        dataType: "json",
        method: "POST",
        url: "RegAutor",
        data: datos
    }).done(function (json) {
        Swal.close();
        console.log("datos consultados: ", json);
        if (json[0].resultado === "exito") {
            $("#aqui_tabla").empty().html(json[0].tabla);
            document.querySelector("#Autores_registradas").textContent = json[0].cuantos;
            $("#tabla_autores").DataTable({
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json"
                }
            });
        } else {
            Swal.fire('Accion no completada', 'No pudo obtener los datos', 'error');
        }
    }).fail(function () {
    }).always(function () {
    });
}

function mostrar_cargando(titulo, mensaje = "") {
    Swal.fire({
        title: titulo,
        html: mensaje,
        timer: 2000,
        timerProgessBar: true,
        didOpen: () => {
            Swal.showLoading();
        },
        willClose: () => {
        }
    }).then((result) => {
        if (result.dismiss === Swal.DismissReason.timer) {
            console.log('I was closed by timer');
        }
    });
}

function eliminar(id) {
    mostrar_cargando("Procesando solicitud", "Espere mientras se eliminan los datos " + id);
   // creando el arreglo llave:valor para el caso:  case "si_eliminalo":
   // también se envía el id para identificar el registro a eliminar.
   
    var datos = {"consultar_datos": "si_eliminalo", "id": id};
    console.log("datos enviados al controlador cuando se va a eliminar "+datos);
    $.ajax({
        dataType: "json",
        method: "POST",
        url: "RegAutor",
        data: datos
    }).done(function (json) {
        Swal.close();
        
        //resultado = obtA.deleteAutor(req.getParameter("id"));
        // if ("exito".equals(resultado)) {
        // json_aElimina.put("resultado", "exito");
        // } else {
        // json_aElimina.put("resultado", "error_eliminar");
        // }
        // json[0].resultado === "exito"   esto se hizo en el controlador  
                    
        if (json[0].resultado === "exito") {
            Swal.fire(
                    'Excelente',
                    'El dato fue eliminado',
                    'success'
                    );
            cargarTabla(); // invocación al método cargarTabla()
        } else {
            Swal.fire(
                    'Error',
                    'No se pudo eliminar el dato intentelo más tarde',
                    'error'
                    );
        }
    }).fail(function () {
        console.log("Error al eliminar");
    }).always(function () {
        console.log("Error al eliminar");
    });
}