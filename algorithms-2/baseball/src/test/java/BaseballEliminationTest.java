import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class BaseballEliminationTest {

   @Test
   public void testNumberOfTeams_singleTeam() {
      assertEquals(1, baseballElimination().numberOfTeams());
   }

   @Test
   public void testNumberOfTeams_multipleTeams() {
      assertEquals(4, baseballElimination("teams4.txt").numberOfTeams());
   }

   @Test
   public void testTeams_singleTeam() {
      Iterator<String> teams = baseballElimination().teams().iterator();
      assertEquals(true, teams.hasNext());
      assertEquals("Turing", teams.next());
      assertEquals(false, teams.hasNext());
   }

   @Test
   public void testTeams_multipleTeams() {
      assertIterableEquals(iterable("Atlanta", "Philadelphia", "New_York", "Montreal"), baseballElimination("teams4.txt").teams());
   }

   @Test
   public void testWins_singleTeam() {
      assertEquals(100, baseballElimination().wins("Turing"));
   }

   @Test
   public void testWins_multipleTeams() {
      BaseballElimination baseballElimination = baseballElimination("teams4.txt");
      assertEquals(83, baseballElimination.wins("Atlanta"));
      assertEquals(80, baseballElimination.wins("Philadelphia"));
      assertEquals(78, baseballElimination.wins("New_York"));
      assertEquals(77, baseballElimination.wins("Montreal"));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testWins_invalidTeam() {
      baseballElimination().wins("Something");
   }

   @Test
   public void testLosses_singleTeam() {
      assertEquals(55, baseballElimination().losses("Turing"));
   }

   @Test
   public void testLosses_multipleTeams() {
      BaseballElimination baseballElimination = baseballElimination("teams4.txt");
      assertEquals(71, baseballElimination.losses("Atlanta"));
      assertEquals(79, baseballElimination.losses("Philadelphia"));
      assertEquals(78, baseballElimination.losses("New_York"));
      assertEquals(82, baseballElimination.losses("Montreal"));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testLosses_invalidTeam() {
      baseballElimination().losses("Something");
   }

   @Test
   public void testRemaining_singleTeam() {
      assertEquals(0, baseballElimination().remaining("Turing"));
   }

   @Test
   public void testRemaining_multipleTeams() {
      BaseballElimination baseballElimination = baseballElimination("teams4.txt");
      assertEquals(8, baseballElimination.remaining("Atlanta"));
      assertEquals(3, baseballElimination.remaining("Philadelphia"));
      assertEquals(6, baseballElimination.remaining("New_York"));
      assertEquals(3, baseballElimination.remaining("Montreal"));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testRemaining_invalidTeam() {
      baseballElimination().losses("Something");
   }

   @Test
   public void testAgainst_singleTeam() {
      assertEquals(0, baseballElimination().against("Turing", "Turing"));
   }

   @Test
   public void testAgainst_multipleTeams() {
      BaseballElimination baseballElimination = baseballElimination("teams4.txt");
      assertEquals(0, baseballElimination.against("Atlanta", "Atlanta"));
      assertEquals(1, baseballElimination.against("Atlanta", "Philadelphia"));
      assertEquals(6, baseballElimination.against("Atlanta", "New_York"));
      assertEquals(1, baseballElimination.against("Atlanta", "Montreal"));

      assertEquals(1, baseballElimination.against("Philadelphia", "Atlanta"));
      assertEquals(0, baseballElimination.against("Philadelphia", "Philadelphia"));
      assertEquals(0, baseballElimination.against("Philadelphia", "New_York"));
      assertEquals(2, baseballElimination.against("Philadelphia", "Montreal"));

      assertEquals(6, baseballElimination.against("New_York", "Atlanta"));
      assertEquals(0, baseballElimination.against("New_York", "Philadelphia"));
      assertEquals(0, baseballElimination.against("New_York", "New_York"));
      assertEquals(0, baseballElimination.against("New_York", "Montreal"));

      assertEquals(1, baseballElimination.against("Montreal", "Atlanta"));
      assertEquals(2, baseballElimination.against("Montreal", "Philadelphia"));
      assertEquals(0, baseballElimination.against("Montreal", "New_York"));
      assertEquals(0, baseballElimination.against("Montreal", "Montreal"));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testAgainst_firstTeamInvalid() {
      baseballElimination("teams4.txt").against("Something", "Atlanta");
   }

   @Test(expected = IllegalArgumentException.class)
   public void testAgainst_secondTeamInvalid() {
     baseballElimination("teams4.txt").against("Atlanta", "Something");
   }

   @Test
   public void testIsEliminated_singleTeam() {
      assertEquals(false, baseballElimination().isEliminated("Turing"));
   }

   @Test
   public void testIsEliminated_twoTeamsTrivialElimination() {
      BaseballElimination baseballElimination = baseballElimination("teams2_trivial.txt");
      assertEquals(false, baseballElimination.isEliminated("A"));
      assertEquals(true, baseballElimination.isEliminated("B"));
   }

   @Test
   public void testIsEliminated_twoTeamsNonTrivialElimination() {
      BaseballElimination baseballElimination = baseballElimination("teams2_nontrivial.txt");
      assertEquals(false, baseballElimination.isEliminated("A"));
      assertEquals(false, baseballElimination.isEliminated("B"));
   }

   @Test
   public void testIsEliminated_multipleTeamsNonTrivialElimination() {
      BaseballElimination baseballElimination = baseballElimination("teams4.txt");
      assertEquals(false, baseballElimination.isEliminated("Atlanta"));
      assertEquals(true, baseballElimination.isEliminated("Philadelphia"));
      assertEquals(false, baseballElimination.isEliminated("New_York"));
      assertEquals(true, baseballElimination.isEliminated("Montreal"));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testIsEliminated_invalidTeam() {
      baseballElimination("teams4.txt").isEliminated("Something");
   }

   @Test
   public void testCertificateOfElimination_singleTeam() {
      assertEquals(null, baseballElimination().certificateOfElimination("Turing"));
   }

   @Test
   public void testCertificateOfElimination_twoTeamsTrivialElimination() {
      BaseballElimination baseballElimination = baseballElimination("teams2_trivial.txt");
      assertEquals(null, baseballElimination.certificateOfElimination("A"));
      assertEquals(iterable("A"), baseballElimination.certificateOfElimination("B"));
   }

   @Test
   public void testCertificateOfElimination_twoNonTeamsTrivialElimination() {
      BaseballElimination baseballElimination = baseballElimination("teams2_nontrivial.txt");
      assertEquals(null, baseballElimination.certificateOfElimination("A"));
      assertEquals(null, baseballElimination.certificateOfElimination("B"));
   }

   @Test
   public void testCertificateOfElimination_multipleTeamsNonTrivialElimination() {
      BaseballElimination baseballElimination = baseballElimination("teams4.txt");
      assertEquals(null, baseballElimination.certificateOfElimination("Atlanta"));
      assertEquals(iterable("Atlanta", "New_York"), baseballElimination.certificateOfElimination("Philadelphia"));
      assertEquals(null, baseballElimination.certificateOfElimination("New_York"));
      assertEquals(iterable("Atlanta"), baseballElimination.certificateOfElimination("Montreal"));
   }

   @Test
   public void testCertificateOfElimination_multipleTeamsNonTrivialElimination_extended() {
      BaseballElimination baseballElimination = baseballElimination("teams5.txt");
      assertEquals(null, baseballElimination.certificateOfElimination("New_York"));
      assertEquals(null, baseballElimination.certificateOfElimination("Baltimore"));
      assertEquals(null, baseballElimination.certificateOfElimination("Boston"));
      assertEquals(null, baseballElimination.certificateOfElimination("Toronto"));
      assertEquals(iterable("New_York", "Baltimore", "Boston", "Toronto"), baseballElimination.certificateOfElimination("Detroit"));
   }

   @Test(expected = IllegalArgumentException.class)
   public void testCertificateOfElimination_invalidTeam() {
      baseballElimination("teams4.txt").certificateOfElimination("Something");
   }

   private BaseballElimination baseballElimination() {
      return baseballElimination("teams1.txt");
   }

   private BaseballElimination baseballElimination(String filename) {
      return new BaseballElimination("src/test/resources/baseball/" + filename);
   }

   private void assertIterableEquals(Iterable<String> expected, Iterable<String> actual) {
      Iterator<String> it = actual.iterator();
      for (String value : expected) {
         assertEquals(value, it.next());
      }
      assertEquals(false, it.hasNext());
   }

   private Iterable<String> iterable(String... args) {
      return Arrays.asList(args);
   }

}
