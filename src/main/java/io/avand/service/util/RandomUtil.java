package io.avand.service.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Utility class for generating random Strings.
 */
public final class RandomUtil {

    private static final int DEF_COUNT = 20;

    private RandomUtil() {
    }

    /**
     * Generate a password.
     *
     * @return the generated password
     */
    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    /**
     * Generate a invitation code.
     *
     * @return the generated invitation code
     */
    public static String generateInvitationCode() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    /**
     * Generate an activation key.
     *
     * @return the generated activation key
     */
    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(6);
    }

    /**
     * Generate a reset key.
     *
     * @return the generated reset key
     */
    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generate an invitation key.
     *
     * @return the generated invitation key
     */
    public static String generateInvitationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    /**
     * Generate an unique key.
     *
     * @return the generated invitation key
     */
    public static String getUniqueId() {
        return RandomStringUtils.randomAlphanumeric(15);
    }


    public static String shortUUID() {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, 10);
    }

    public static String customShortUUID(int length) {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, length);
    }

}
