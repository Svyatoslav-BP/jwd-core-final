package com.epam.jwd.core_final.domain;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */
public class ApplicationProperties {
    private String inputRootDir;
    private String outputRootDir;
    private String crewFileName;
    private String missionsFileName;
    private String spaceshipsFileName;
    private Integer fileRefreshRate;
    private String dateTimeFormat;

    public ApplicationProperties() {
    }

    public ApplicationProperties(String inputRootDir, String outputRootDir, String crewFileName, String missionsFileName, String spaceshipsFileName, Integer fileRefreshRate, String dateTimeFormat) {
        this.inputRootDir = inputRootDir;
        this.outputRootDir = outputRootDir;
        this.crewFileName = crewFileName;
        this.missionsFileName = missionsFileName;
        this.spaceshipsFileName = spaceshipsFileName;
        this.fileRefreshRate = fileRefreshRate;
        this.dateTimeFormat = dateTimeFormat;
    }

}
