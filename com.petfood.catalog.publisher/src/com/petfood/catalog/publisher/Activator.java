package com.petfood.catalog.publisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.petfood.catalog.util.CatalogServiceLogger;

public class Activator implements BundleActivator {
    private ServiceRegistration<CatalogService> serviceRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        // Log that the publisher has started
        CatalogServiceLogger.success("Publisher", "Catalog Service Publisher Started");

        // Create an instance of the CatalogService implementation
        CatalogService catalogService = new CatalogServiceImpl();

        // Register the CatalogService as an OSGi service
        serviceRegistration = context.registerService(CatalogService.class, catalogService, null);

        // Log that the service has been registered
        CatalogServiceLogger.info("Publisher", "CatalogService registered successfully");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        // Log that the publisher is stopping
        CatalogServiceLogger.warning("Publisher", "Catalog Service Publisher Stopped");

        // Unregister the CatalogService
        if (serviceRegistration != null) {
            serviceRegistration.unregister();
            CatalogServiceLogger.info("Publisher", "CatalogService unregistered successfully");
        }
    }
}