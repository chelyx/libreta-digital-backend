package com.g5311.libretadigital.utils;

public class EmailTemplates {

    public static final String BFA_SELLO = """
            Hola ${nombre},

             Le enviamos adjunta la nota sellada junto con su correspondiente sello de tiempo emitido por BFA.

             Archivos incluidos:

             ${json} → Contenido original sellado

             ${rd} → Recibo de tiempo oficial

             Puede verificar ambos documentos en el siguiente enlace:
             https://bfa.ar/sello#tab_3

             Saludos cordiales,
             SIRCA
             """;
    public static final String NOTA_ALUMNO = """
            Hola ${nombre},

             Le informamos que ya se encuentra disponible la calificación correspondiente al examen final de ${curso}, realizado el día ${fecha}.

             • Nota obtenida: ${nota}
             • Versión de la nota: ${version}

             Esta información también puede consultarse en su historial académico.

             Saludos cordiales,
             SIRCA
             """;
}
