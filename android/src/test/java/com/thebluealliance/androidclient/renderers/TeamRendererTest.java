package com.thebluealliance.androidclient.renderers;

import com.thebluealliance.androidclient.datafeed.APICache;
import com.thebluealliance.androidclient.datafeed.framework.ModelMaker;
import com.thebluealliance.androidclient.helpers.ModelType;
import com.thebluealliance.androidclient.listitems.TeamListElement;
import com.thebluealliance.androidclient.models.Team;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TeamRendererTest {

    private static final String TEAM_KEY = "frc1124";

    @Mock APICache mDatafeed;
    private Team mTeam;
    private TeamRenderer mRenderer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mTeam = ModelMaker.getModel(Team.class, TEAM_KEY);
        mRenderer = new TeamRenderer(mDatafeed);
    }

    @Test
    public void testRenderFromKey() {
        when(mDatafeed.fetchTeam(TEAM_KEY)).thenReturn(Observable.just(mTeam));
        TeamListElement element = mRenderer.renderFromKey(TEAM_KEY, ModelType.TEAM);
        assertTeamElement(element, false);
    }

    @Test
    public void testNullRenderFromKey() {
        when(mDatafeed.fetchTeam(TEAM_KEY)).thenReturn(Observable.just(null));
        TeamListElement element = mRenderer.renderFromKey(TEAM_KEY, ModelType.TEAM);
        assertNull(element);
    }

    @Test
    public void testRenderFromModel() {
        TeamListElement element = mRenderer.renderFromModel(mTeam, true);
        assertTeamElement(element, true);
    }

    private void assertTeamElement(TeamListElement element, boolean showDetails) {
        assertNotNull(element);
        assertEquals(element.getKey(), TEAM_KEY);
        assertEquals(element.mTeamLocation, "Avon, Connecticut, USA");
        assertEquals(element.mShowLinkToTeamDetails, showDetails);
        assertEquals(element.mTeamName, "UberBots");
        assertEquals(element.mTeamNumber, 1124);
    }
}