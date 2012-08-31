package org.commonjava.aprox.core.model.io;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import org.commonjava.aprox.model.DeployPoint;
import org.commonjava.aprox.model.Group;
import org.commonjava.aprox.model.Repository;
import org.commonjava.aprox.model.io.StoreKeySerializer;
import org.commonjava.web.json.model.Listing;
import org.commonjava.web.json.ser.JsonSerializer;

import com.google.gson.reflect.TypeToken;

@Singleton
public class AProxModelSerializer
{
    private static final TypeToken<Listing<Repository>> REPO_LISTING_TYPE_TOKEN = new TypeToken<Listing<Repository>>()
    {
    };

    private static final TypeToken<Listing<Group>> GROUP_LISTING_TYPE_TOKEN = new TypeToken<Listing<Group>>()
    {
    };

    private static final TypeToken<Listing<DeployPoint>> DEPLOY_POINT_LISTING_TYPE_TOKEN =
        new TypeToken<Listing<DeployPoint>>()
        {
        };

    @Inject
    private JsonSerializer restSerializer;

    @PostConstruct
    protected void registerSerializationAdapters()
    {
        restSerializer.registerSerializationAdapters( new StoreKeySerializer() );
    }

    @SuppressWarnings( "unchecked" )
    public Repository repositoryFromRequestBody( final HttpServletRequest request )
    {
        return restSerializer.fromRequestBody( request, Repository.class );
    }

    @SuppressWarnings( "unchecked" )
    public Group groupFromRequestBody( final HttpServletRequest request )
    {
        return restSerializer.fromRequestBody( request, Group.class );
    }

    @SuppressWarnings( "unchecked" )
    public DeployPoint deployPointFromRequestBody( final HttpServletRequest request )
    {
        return restSerializer.fromRequestBody( request, DeployPoint.class );
    }

    public String repoListingToString( final Listing<Repository> listing )
    {
        return restSerializer.toString( listing, REPO_LISTING_TYPE_TOKEN.getType() );
    }

    public String toString( final Repository repo )
    {
        return restSerializer.toString( repo );
    }

    public String groupListingToString( final Listing<Group> listing )
    {
        return restSerializer.toString( listing, GROUP_LISTING_TYPE_TOKEN.getType() );
    }

    public String deployPointListingToString( final Listing<DeployPoint> listing )
    {
        return restSerializer.toString( listing, DEPLOY_POINT_LISTING_TYPE_TOKEN.getType() );
    }

    public String toString( final Group group )
    {
        return restSerializer.toString( group );
    }

    public String toString( final DeployPoint deploy )
    {
        return restSerializer.toString( deploy );
    }

}
