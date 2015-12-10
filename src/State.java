/**
 * Created by Mio on 2015/12/9.
 */
public enum State {
    TIME, LOCATION, PERSON_NAME, ORG_NAME, COMPANY_NAME, PRODUCT_NAME, NONE;

    @Override
    public String toString() {
        return super.toString();
    }

    public static State fromString(String s) {
        s = s.toUpperCase();
        for (State state : State.values()) {
            if (state.toString().equals(s))
                return state;

        }

        return NONE;
    }
}
