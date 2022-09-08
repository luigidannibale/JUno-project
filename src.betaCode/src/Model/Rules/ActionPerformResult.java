package Model.Rules;

/**
 * Describes the possibles results after playing an {@link Model.Cards.Card}:
 * <ul>
 *     <li>SUCCESSFUL</li>
 *     <li>NO_COLOR_PROVIDED</li>
 *     <li>NO_PLAYER_PROVIDED</li>
 *     <li>PLAYER_WON</li>
 * </ul>
 *
 * @author D'annibale Luigi, Venturini Daniale
 */
public enum ActionPerformResult
{
    SUCCESSFUL,
    NO_COLOR_PROVIDED,
    NO_PLAYER_PROVIDED,
    PLAYER_WON;
}
