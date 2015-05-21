package org.commonjava.aprox.implrepo.maint;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.commonjava.aprox.implrepo.client.ImpliedRepoClientModule;
import org.commonjava.aprox.model.core.Group;
import org.commonjava.aprox.model.core.RemoteRepository;
import org.commonjava.aprox.model.core.StoreKey;
import org.commonjava.aprox.model.core.StoreType;
import org.junit.Test;

public class CreateGroupWithMemberImplicationsTest
    extends AbstractMaintFunctionalTest
{
    private static final String IMPLIED = "implied-repo";

    private static final String TEST_REPO = "test";

    @Test
    public void groupUpdated()
        throws Exception
    {
        System.out.println( "\n\n\n\n\nSTARTING: " + name.getMethodName() + "\n\n\n\n\n" );
        final StoreKey impliedKey = new StoreKey( StoreType.remote, IMPLIED );
        final StoreKey testKey = new StoreKey( StoreType.remote, TEST_REPO );

        testRepo =
            client.stores()
                  .create( new RemoteRepository( TEST_REPO, "http://www.bar.com/repo" ), setupChangelog,
                           RemoteRepository.class );

        client.stores()
              .create( new RemoteRepository( IMPLIED, "http://www.foo.com/repo" ), setupChangelog,
                       RemoteRepository.class );

        client.module( ImpliedRepoClientModule.class )
              .setStoresImpliedBy( testRepo, Arrays.asList( impliedKey ), "setting store implications" );

        pubGroup.addConstituent( testKey );

        client.stores()
              .update( pubGroup, "Add test repo that has implied repos" );

        //        Thread.sleep( 1000 );

        pubGroup = client.stores()
                         .load( StoreType.group, PUBLIC, Group.class );

        assertThat( pubGroup.getConstituents()
                            .contains( impliedKey ), equalTo( true ) );
        System.out.println( "\n\n\n\n\nENDED: " + name.getMethodName() + "\n\n\n\n\n" );
    }

}
