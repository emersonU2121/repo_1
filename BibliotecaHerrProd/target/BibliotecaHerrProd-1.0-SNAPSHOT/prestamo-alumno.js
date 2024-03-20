const btnAddPrestamo = document.querySelector("#registrar_prestamo");
const formularioPrestamo = document.querySelector("#formulario_prestamo");
const comboAlumno = document.querySelector("#carnetAlumno");
const comboLibro = document.querySelector("#codigoLibro");
const fechaPrestamo = document.querySelector("#fechaPrestamo");
const fechaDevolucion = document.querySelector("#fechaDevolucion");
const title = document.querySelector("#exampleModalLabel");
const error = document.querySelector("#error");

document.addEventListener("DOMContentLoaded", () => {
    cargarCombos();
    cargarDatos();
    $('#carnetAlumno').select2();
    $('#codigoLibro').select2();
    $('#formulario_prestamo').parsley();
});



btnAddPrestamo.addEventListener('click', () => {
    formularioPrestamo.reset();
    document.querySelector("#codigoPrestamo").readOnly = false;
    $('#formulario_prestamo').parsley().reset();
    $('#carnetAlumno').val("").trigger('change');
    $('#codigoLibro').val("").trigger('change');
    title.innerHTML = "<h5 class='modal-title' id='exampleModalLabel'>Registro nuevo Prestamo<br><sub> Todos los campos son obligatorios</sub>";
    fechaDevolucion.readOnly = true;
    $("#md_registrar_prestamo").modal("show");
});

const cargarCombos = () => {
    var datos = {"opcion": "cargarCombos"};

    $.ajax({
        dataType: "json",
        method: "POST",
        url: "PrestamosLibros",
        data: datos
    }).done(function (json) {
        if (json[0].resultado === "exito") {
            comboAlumno.innerHTML += json[0].alumnos;
            comboLibro.innerHTML += json[0].libros;

        } else {
            console.log(json[0]);
        }
    }).fail(function () {
    });

};







const cargarDatos = () => {
    mostrar_cargando("Procesando Solicitud", "Espere un momento mientras se obtiene la información solicitada");
    const datos = {"opcion": "consultar"};
    $.ajax({
        dataType: "json",
        method: "POST",
        url: "PrestamosLibros",
        data: datos
    }).done(function (json) {
        if (json[0].resultado === "exito") {
            $("#tablita").empty().html(json[0].tabla);
            $("#tabla_prestamos").DataTable({
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json"
                }
            });
            document.querySelector("#prestamos_registrados").textContent = json[0].cantidad;
        } else {
            Swal.fire(
                    "Error",
                    "No se pudo completar la petición, intentelo más tarde",
                    "error"
                    );
        }
    }).fail(function () {
    });
};

fechaPrestamo.addEventListener("change", () => {
    fechaDevolucion.readOnly = false;
    var fechaPrestamoValue = new Date(fechaPrestamo.value);
    fechaPrestamoValue.setDate(fechaPrestamoValue.getDate() + 1);
    fechaDevolucion.setAttribute("min", fechaPrestamoValue.toISOString().slice(0, 10));
});

$('#codigoLibro').on('select2:select', (e) => {
    document.querySelector("#cantidad").value = 0;
    document.querySelector("#cantidad").setAttribute("max", e.params.data.element.attributes[0].nodeValue);
});


formularioPrestamo.addEventListener("submit", (e) => {
    e.preventDefault();
    if (!$('#codigoLibro').parsley().isValid() ||
            !$('#carnetAlumno').parsley().isValid() ||
            !$('#codigoPrestamo').parsley().isValid() ||
            !$('#cantidad').parsley().isValid() ||
            !$('#fechaPrestamo').parsley().isValid()
            ) {
        e.preventDefault();
        return;
    }

    if (fechaDevolucion.value === "") {
        error.classList.remove('hidden');
        return;
    } else {
        error.classList.add('hidden');
    }

    const datos = $("#formulario_prestamo").serialize();
    console.log("DATOS A INSERTAR/MODIFICAR " + datos);
    if (!document.querySelector("#codigoPrestamo").readOnly) { //INSERTAR
        $.ajax({
            dataType: "json",
            method: "POST",
            url: "PrestamosLibros",
            data: datos


        }).done(function (json) {
            if (json[0].resultado === "exito") {
                Swal.fire({
                    icon: 'success',
                    title: 'Prestamo Registrado',
                    showConfirmButton: false,
                    timer: 1500
                });
                formularioPrestamo.reset();
                $("#md_registrar_prestamo").modal("hide");
                setTimeout(() => {
                    cargarDatos();
                }, 1500);
            } else {
                Swal.fire({
                    icon: 'info',
                    title: 'No se logró insertar el registro',
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        }).fail(function () {
        });
// modificar
    } else {
        $.ajax({
            dataType: "json",
            method: "POST",
            url: "PrestamosLibros",
            data: datos



        }).done(function (json) {
            if (json[0].resultado === "exito") {
                $("#opcion").val("insertar");
                Swal.fire({
                    icon: 'success',
                    title: 'Prestamo Actualizado',
                    showConfirmButton: false,
                    timer: 1500
                });
                formularioPrestamo.reset();
                $("#md_registrar_prestamo").modal("hide");
                setTimeout(() => {
                    cargarDatos();
                }, 1500);
            } else {
                Swal.fire({
                    icon: 'info',
                    title: 'No se logró actualizar el registro',
                    showConfirmButton: false,
                    timer: 1500
                });
            }
        }).fail(function () {
        });
    }
});


document.addEventListener("click", (e) => {


    if (e.target.classList.contains("btn_editar")) {
        $('#formulario_prestamo').parsley().reset();
        title.innerHTML = "<h5 class='modal-title' id='exampleModalLabel'>Editar Prestamo<br><sub> Todos los campos son obligatorios</sub>";
        const id = e.target.getAttribute("data-id");
        var datos = {"opcion": "editar_consultar", "id": id};
        $.ajax({
            dataType: "json",
            method: "POST",
            url: "PrestamosLibros",
            data: datos
        }).done(function (json) {
            if (json[0].resultado === "exito") {
                document.querySelector("#codigoPrestamo").value = json[0].prestamo.codigoPrestamo;
                document.querySelector("#carnetAlumno").value = json[0].prestamo.carnet;
                document.querySelector("#codigoLibro").value = json[0].prestamo.codigoLibro;
                document.querySelector("#fechaPrestamo").value = json[0].prestamo.fechaPrestamo;
                document.querySelector("#fechaDevolucion").value = json[0].prestamo.fechaDevolucion;
                document.querySelector("#cantidad").value = json[0].prestamo.cantidad;
                document.querySelector("#codigoPrestamo").readOnly = true;
                document.querySelector("#cantidad").setAttribute('max', comboLibro.children[comboLibro.selectedIndex].getAttribute('data-max'))
                $('#carnetAlumno').val(json[0].prestamo.carnet).trigger('change');
                $('#codigoLibro').val(json[0].prestamo.codigoLibro).trigger('change');
                fechaDevolucion.setAttribute("min", fechaPrestamo.value);
                $("#md_registrar_prestamo").modal("show");



                $("#opcion").val("si_actualizalo");



            } else {
                Swal.fire(
                        "Error",
                        "No se pudo completar la petición, intentelo más tarde",
                        "error"
                        );
            }
        }).fail(function () {
        }).always(function () {
        });
    }




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
                eliminar($(this).attr('data-id'));
            } else if (result.isDenied) {
                Swal.fire("Opcion cancelada por el usuario", '', 'info');
            }
        });
    });






});




function eliminar(id) {
    mostrar_cargando("Procesando solicitud", "Espere mientras se eliminan los datos " + id);
    // var datos = {"consultar_datos": "si_eliminalo", "id": id};
    //  const id = e.target.getAttribute("data-id");
    var datos = {"opcion": "eliminar", "id": id};
    console.log("id a eliminar es: " + datos.id);
    $.ajax({
        dataType: "json",
        method: "POST",
        url: "PrestamosLibros",
        data: datos
    }).done(function (json) {
        Swal.close();
        if (json[0].resultado === "exito") {
            Swal.fire(
                    'Excelente',
                    'El dato fue eliminado',
                    'success'
                    );
            cargarDatos();
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







function mostrar_cargando(titulo, mensaje = "") {
    Swal.fire({
        title: titulo,
        html: mensaje,
        timer: 2000,
        timerProgressBar: true,
        didOpen: () => {
            Swal.showLoading();
        },
        willClose: () => {
        }
    }).then((result) => {
        if (result.dismiss === Swal.DismissReason.timer) {
        }
    });
}