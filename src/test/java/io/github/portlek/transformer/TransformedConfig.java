package io.github.portlek.transformer;

import io.github.portlek.replaceable.RpList;
import io.github.portlek.replaceable.RpString;
import io.github.portlek.transformer.annotations.Comment;
import io.github.portlek.transformer.annotations.CustomKey;
import io.github.portlek.transformer.annotations.Migration;
import io.github.portlek.transformer.annotations.Names;
import io.github.portlek.transformer.annotations.Version;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Version(5)
@Names(modifier = Names.Modifier.TO_LOWER_CASE, strategy = Names.Strategy.HYPHEN_CASE)
public class TransformedConfig extends TransformedObject {

  /**
   * The section contains default messages/titles for entering/quiting to/from claims.
   */
  @Comment("The section contains default messages/titles for entering/quiting to/from claims.")
  public static DefaultMessages defaultMessages = new DefaultMessages();

  /**
   * The section contains disabled commands in claims.
   */
  @Comment("The section contains disabled commands in claims.")
  public static Set<String> disabledCommandsInClaims = Set.of(
    "sethome");

  /**
   * The section contains worlds which are forbidden to create claims in it.
   */
  @Comment(
    "The section contains worlds which are forbidden to create claims in it."
  )
  public static Set<String> disabledWorlds = Set.of(
    "spawn",
    "lobby");

  /**
   * The section contains mobs with its limit amount.
   * <p>
   * This limit will only apply in chunks of the all players.
   */
  @Comment({
    "The section contains mobs with its limit amount.",
    "This limit will apply in chunks of the all players.",
    "You can find the mob names here https://mcreator.net/wiki/entity-ids",
    "You should use the 'Registry name' as key."
  })
  public static Map<String, Integer> mobLimits = Map.of(
    "cow", 5);

  /**
   * The section enables PvP mode in claims.
   */
  @Comment("The section enables PvP mode in claims.")
  public static boolean pvpInClaims = true;

  /**
   * the class that represents default messages for entering/quiting to/from claims.
   */
  public static final class DefaultMessages extends TransformedObject {

    /**
     * The section contains message for entering to claims.
     */
    @Comment("The section contains message for entering to claims.")
    public static RpString enteringMessage = RpString.from("");

    /**
     * The section enables to the sending message for entering/quiting to/from claims.
     */
    @Comment("The section enables to the sending message for entering/quiting to/from claims.")
    public static boolean messageEnabled = false;

    /**
     * The section contains title message for quiting from claims.
     */
    @Comment("The section contains title message for quiting from claims.")
    public static RpString quitingMessage = RpString.from("");

    /**
     * The section enables to the sending title for entering/quiting to/from claims.
     */
    @Comment("The section enables to the sending title for entering/quiting to/from claims.")
    public static boolean titleEnabled = true;
  }
}
