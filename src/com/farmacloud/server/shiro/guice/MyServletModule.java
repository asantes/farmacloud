package com.farmacloud.server.shiro.guice;

import com.farmacloud.server.services.FileUpload;
import com.farmacloud.server.services.FileUploadProveedor;
import com.farmacloud.server.services.ImpServicioGestionFarmacia;
import com.farmacloud.server.services.ImpServicioGestionMedicamento;
import com.farmacloud.server.services.ImpServicioGestionProveedor;
import com.farmacloud.server.services.ImpServicioUsuario;
import com.google.inject.servlet.ServletModule;

public class MyServletModule extends ServletModule
{
    @Override
    protected void configureServlets()
    {
    	serve("/farmacloud/gestionusuario").with(ImpServicioUsuario.class);
    	serve("/farmacloud/fileUpload").with(FileUpload.class);
    	serve("/farmacloud/fileUploadProveedor").with(FileUploadProveedor.class);
    	serve("/farmacloud/gestionproveedor").with(ImpServicioGestionProveedor.class);
    	serve("/farmacloud/gestionmedicamento").with(ImpServicioGestionMedicamento.class);
    	serve("/farmacloud/gestionfarmacia").with(ImpServicioGestionFarmacia.class);
    } 
}