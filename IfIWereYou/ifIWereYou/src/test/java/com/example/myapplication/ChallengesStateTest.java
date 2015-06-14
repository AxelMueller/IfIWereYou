package com.example.myapplication;

import com.ifiwereyou.BuildConfig;
import com.ifiwereyou.objects.Challenge;
import com.ifiwereyou.objects.ChallengeState;
import com.parse.ParseUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

/**
 * Created by D060426 on 08.06.2015.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "build/intermediates/manifests/debug/AndroidManifest.xml", resourceDir = "../../../../build/intermediates/res/debug" , emulateSdk = 18, reportSdk = 18)
public class ChallengesStateTest {

    Challenge challenge;
    ParseUser parseUser;
    @Before
    public void setup() {
        challenge = new Challenge();
        parseUser = new ParseUser();
        parseUser.setUsername("test");
        parseUser.setEmail("test@test.de");
        parseUser.setPassword("test");
        parseUser.setObjectId("1234");
        challenge.setChallenged(parseUser);
        challenge.setChallenger(parseUser);
        challenge.setChallengeText("test");
    }

    @Test
    public void checkChallengeInitialState() throws Exception {
        assertTrue(challenge.getChallengeState().getState() == ChallengeState.state.NEW);
    }

    @Test
      public void acceptChallenge() throws Exception {
        challenge.accept();
        assertTrue(challenge.getChallengeState().getState() == ChallengeState.state.ACCEPTED);
    }

    @Test
    public void acceptWithOtherUser() throws Exception {
        parseUser.setObjectId("4321");
        challenge.accept();
        assertTrue(challenge.getChallengeState().getState() == ChallengeState.state.NEW);
    }

    @Test
    public void declineChallenge() throws Exception {
        challenge.decline();
        assertTrue(challenge.getChallengeState().getState() == ChallengeState.state.DECLINED);
    }

    @Test
    public void cancelChallenge() throws Exception {
        challenge.accept();
        challenge.cancel();
        assertTrue(challenge.getChallengeState().getState() == ChallengeState.state.CANCELED);
    }

    @Test
    public void cancelWithoutAcceptingChallenge() throws Exception {
        challenge.cancel();
        assertTrue(challenge.getChallengeState().getState() == ChallengeState.state.NEW);
    }

    @Test
    public void fulfillChallenge() throws Exception {
        challenge.accept();
        challenge.fulfill();
        assertTrue(challenge.getChallengeState().getState() == ChallengeState.state.FULFILLED);
    }

}

