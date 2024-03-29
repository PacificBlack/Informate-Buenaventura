package com.pacificblack.informatebuenaventura.fragments.quienes;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pacificblack.informatebuenaventura.R;

import org.w3c.dom.Text;


public class QuienesFragment extends Fragment {

    TextView terminos;


    public QuienesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_quienes, container, false);

        terminos = vista.findViewById(R.id.terminos_texto);
        terminos.setText("Terminos y Condiciones de Informate Buenaventura\n" +
                "\n" +
                "\n" +
                "\n" +
                "1. Aceptación de los Términos del Servicio y Normas de la Comunidad que a continuación se detallan:\n" +
                "\n" +
                "POR FAVOR LEA CON DETENIMIENTO LAS CONDICIONES DE USO ANTES DE UTILIZAR LA PRESENTE APLICACIÓN. PUEDEN PARECERLE MUY TÉCNICAS Y FORMALES DESDE EL PUNTO DE VISTA LEGAL PERO SON DE VITAL IMPORTANCIA. AL UTILIZAR ESTE SITIO, USTED ACEPTA ESTAS CONDICIONES DE USO. EN CASO DE QUE USTED, NO ESTE DE ACUERDO CON ESTAS CONDICIONES DE USO, POR FAVOR NO UTILICE ESTA APLICACIÓN O LOS SERVICIOS QUE ESTA LE OFRECE.\n" +
                "\n" +
                "1. La aceptación a estos términos del servicio (“Términos de Servicio”) es un acuerdo legal vinculante entre usted y la EMPRESA respecto al uso de la aplicación y todos los productos o servicios disponibles de la misma. Por favor, lea estos Términos de Servicio cuidadosamente. Al acceder o utilizar la aplicación, usted expresa su acuerdo con (1) los Términos del Servicio, y (2) las Normas de la comunidad incorporadas y detalladas en las presentes condiciones generales. Si no está de acuerdo con cualquiera de estos términos o las Normas de la comunidad, por favor, no utilice esta aplicación o los servicios que el mismo ofrece.\n" +
                "\n" +
                "2. Actualización de los Términos del Servicio. Aunque intentaremos notificar a los lectores cuando se realizan cambios importantes a las presentes Condiciones de Servicio, usted debe revisar periódicamente la versión vigente más actualizada de las Condiciones del servicio. La EMPRESA, a su discreción, puede modificar o revisar estas Condiciones de servicio y políticas en cualquier momento, y usted acepta que quedará vinculado por estas modificaciones o revisiones.\n" +
                "\n" +
                "3. Las presentes Condiciones de Servicio se aplican a todos los usuarios de la aplicación, incluidos los usuarios que participen aportando contenidos tales como imágenes, información y otros materiales o servicios en la aplicación.\n" +
                "\n" +
                "4. La EMPRESA se reserva el derecho a modificar cualquier aspecto de la aplicación en cualquier momento.\n" +
                "\n" +
                "2. Normas de la Comunidad:\n" +
                "\n" +
                "1. El Usuario se compromete a cumplir con los términos y condiciones de estos Términos de Servicio, Normas de la comunidad, y todas las leyes locales, nacionales y reglamentos internacionales.\n" +
                "\n" +
                "2. El Usuario se compromete a no hacerse pasar por otra persona u organización, lo que puede constituir un delito de suplantación de identidad.\n" +
                "\n" +
                "3. El Usuario se compromete a no subir contenido inapropiado o ilegal.\n" +
                "\n" +
                "4. El Usuario se compromete a no eludir, desactivar o interferir en las funciones relacionadas con la seguridad de la aplicación que impidan o limiten el uso o copia de cualquier Contenido o hacer cumplir las limitaciones del uso de Informate Buenaventura o su Contenido en el mismo.\n" +
                "\n" +
                "5. No se permite el uso de la firma o avatar como medio de promoción o publicidad de productos, servicios, programas de afiliados o webs, en el caso de que contengan publicidad o tengan fines comerciales.\n" +
                "\n" +
                "6. El uso de esta aplicación como medio para organizar ataques o spam a cualquier tipo de servicio (foros, webs, etc) no está permitido. Ese tipo de contenidos podrán ser eliminados.\n" +
                "\n" +
                "7. El administrador de la aplicación tienen el derecho a borrar, editar, mover o cerrar cualquier contenido que incumpla cualquier de las normas y obligaciones descritas en estos términos legales o pueda ser considerado inapropiado por la EMPRESA.\n" +
                "\n" +
                "8. No son admisibles mensajes con amenazas, insultos graves o cualquier otro tipo de comentario que pueda herir la sensibilidad del destinatario. En tal caso, nos reservamos el derecho de avisar a las autoridades pertinentes.\n" +
                "\n" +
                "\n" +
                "3. Propiedad intelectual e industrial de la aplicación:\n" +
                "\n" +
                "1. La EMPRESA es titular de los derechos de propiedad intelectual e industrial, o ha obtenido las autorizaciones o licencias correspondientes para su explotación, sobre el nombre de dominio, las marcas y signos distintivos, la aplicación y el resto de obras e invenciones asociadas con la aplicación y la tecnología asociada al mismo, así como sobre sus contenidos, con excepción de las obras y contenidos generados por los usuarios, que pertenecen a sus correspondientes autores, sin perjuicio de los derechos de explotación de los mismos que corresponden a la EMPRESA.\n" +
                "\n" +
                "2. Los contenidos de esta aplicación, incluyendo diseños, aplicaciones, texto, imágenes, cómics y código fuente (“Contenido”), están protegidos por derechos de propiedad intelectual.\n" +
                "\n" +
                "3. Los contenidos no se podrán utilizar, reproducir, copiar o transmitir en forma alguna por un tercero sin el permiso previo, escrito y explícito de la EMPRESA. En especial, dichos contenidos no se podrán utilizar, integrar, capturar o reproducir, salvo en caso de que se integren en la interfaz de una aplicación, página web o sistema que mantenga, muestre, publique o explote otros contenidos relevantes o principales, siendo en este caso la importancia de los contenidos publicados por la EMPRESA accesoria, anecdótica o irrelevante frente a aquéllos. Esta excepción no tendrá efecto cuando el tercero titular de la aplicación, página web o sistema desarrolle una actividad económica o empresarial u obtenga cualquier tipo de lucro, directo o indirecto, asociado a la misma, en cuyo caso la autorización deberá ser necesariamente recabada de la empresa con carácter previo, escrito y expreso.\n" +
                "\n" +
                "4. La titularidad de los contenidos introducidos por los usuarios pertenece a sus correspondientes autores. La EMPRESA no se responsabiliza de las opiniones emitidas por los autores de dichos contenidos.\n" +
                "\n" +
                "5. Las marcas de terceros que, eventualmente, pueden aparecer en la aplicación pertenecen a los terceros titulares de las mismas.\n" +
                "\n" +
                "4. Propiedad Intelectual de los Archivos de Usuarios:\n" +
                "\n" +
                "1. Usted puede enviar imágenes y texto (“Comentarios de usuarios”) a la aplicación. Las fotos, comentarios o cualquier otra obra o material que incorporen los usuarios se conocen colectivamente como “Archivos de Usuario”.\n" +
                "\n" +
                "2. Usted acepta que en caso de ser publicados dichos Archivos de Usuario, serán puestos a libre disposición del resto de usuarios de la aplicación, sin limitación alguna.\n" +
                "\n" +
                "3. El Usuario es el único responsable de los Archivos de Usuario remitidos y acepta las consecuencias de su envío a la aplicación y de su publicación. El usuario afirma, y / o garantiza ser dueño y/o disponer de todos los derechos necesarios para la publicación de los archivos de Usuario en la aplicación, autorizando, por tanto, a la empresa para su comunicación pública, uso y explotación en la forma que estimen conveniente, sin limitación alguna geográfica o temporal.\n" +
                "\n" +
                "4. Dicha autorización, que, en su caso, revestirá la forma legal de licencia perpetua, irrevocable, mundial, no exclusiva, gratuita, sublicenciable y transferible para usar, reproducir, distribuir, modificar, adaptar, traducir y, bajo cualquier otra forma, explotar los Archivos de Usuario, incluida la promoción y redistribución de parte o la totalidad de la aplicación en cualquier formato y a través de cualquier canal de comunicación.\n" +
                "\n" +
                "5. Cualquier tercero distinto de la EMPRESA o de las personas físicas o jurídicas expresamente autorizadas por la misma que pretenda extraer, usar, publicar o explotar, bajo cualquier forma, los contenidos generados por los usuarios, deberá recabar, previa y expresamente, el consentimiento de sus titulares o, en su caso, de la EMPRESA.\n" +
                "\n" +
                "6. El Usuario se compromete a no presentar material alguno que ostente derechos de propiedad intelectual o industrial o que estén protegidos por el secreto comercial o de cualquier otro tipo, incluyendo la privacidad y derechos de publicidad, salvo en el caso de que sea el propietario de dichos derechos o tenga el permiso de su titular para publicar el material y conceder a la aplicacipon todos los derechos de licencia otorgados en este documento.\n" +
                "\n" +
                "5. Exclusión de responsabilidad:\n" +
                "\n" +
                "1. El Usuario entiende que al utilizar la aplicación, la EMPRESA no es responsable de la exactitud, utilidad, seguridad o derechos de propiedad intelectual de o en relación con los Archivos de Usuario. El Usuario entiende y reconoce que los Archivos de Usuarios pueden resultar inexactos, ofensivos y en algunos casos resultar indecentes o censurables.\n" +
                "\n" +
                "2. la aplicación puede contener enlaces a sitios web de terceros que no son propiedad o no son controladas por la EMPRESA, quien carece de control sobre, y no asume ninguna responsabilidad por el contenido, políticas de privacidad o prácticas de los sitios web de terceros. Además, la EMPRESA no puede censurar o editar el contenido de cualquier sitio de terceros. Mediante el uso de la aplicación expresamente excluye a la EMPRESA de toda y cualquier responsabilidad que surja del uso de cualquier sitio web de terceros.\n" +
                "\n" +
                "3. La EMPRESA no comparte ni hace propios, de manera enumerativa pero no limitativa, los envíos de usuario ni las entradas, recomendaciones, consejos y opiniones expresados en los Archivos de Usuario, eximiéndose la EMPRESA de toda responsabilidad que se produzca por la publicación en la aplicación de los Archivos de los Usuarios.\n" +
                "\n" +
                "4. La aplicación no permite las actividades infractoras de los derechos de autor en su Sitio Web, y la EMPRESA tiene la potestad de borrar todos los contenidos y envíos de usuario que infrinjan estos derechos. la aplicación se reserva el derecho de eliminar Contenido de Usuario sin previo aviso, en caso de que existan dudas acerca del cumplimiento de las presentes condiciones de uso.");

        return vista;
    }

}
