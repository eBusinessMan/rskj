/*
 * This file is part of RskJ
 * Copyright (C) 2017 RSK Labs Ltd.
 * (derived from ethereumJ library, Copyright (c) 2016 <ether.camp>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.ethereum.jsontestsuite;

import org.ethereum.config.SystemProperties;
import org.ethereum.config.blockchain.GenesisConfig;
import org.ethereum.config.net.MainNetConfig;
import org.ethereum.core.BlockHeader;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Mikhail Kalinin
 * @since 02.09.2015
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class GitHubBasicTest {

    private static final Logger logger = LoggerFactory.getLogger("TCK-Test");
    public String shacommit = "99afe8f5aad7bca5d0f1b1685390a4dea32d73c3";

    @After
    public void recover() {
        SystemProperties.CONFIG.setBlockchainConfig(MainNetConfig.INSTANCE);
    }

    @Test
    public void runDifficultyTest() throws IOException, ParseException {

        SystemProperties.CONFIG.setBlockchainConfig(MainNetConfig.INSTANCE);

        String json = JSONReader.loadJSONFromCommit("BasicTests/difficulty.json", shacommit);

        DifficultyTestSuite testSuite = new DifficultyTestSuite(json);

        for (DifficultyTestCase testCase : testSuite.getTestCases()) {

            logger.info("Running {}\n", testCase.getName());

            BlockHeader current = testCase.getCurrent();
            BlockHeader parent = testCase.getParent();

            assertEquals(testCase.getExpectedDifficulty(), current.calcDifficulty(parent));
        }
    }

    @Test
    public void runDifficultyFrontierTest() throws IOException, ParseException {

        SystemProperties.CONFIG.setBlockchainConfig(MainNetConfig.INSTANCE);

        String json = JSONReader.loadJSONFromCommit("BasicTests/difficultyFrontier.json", shacommit);

        DifficultyTestSuite testSuite = new DifficultyTestSuite(json);

        for (DifficultyTestCase testCase : testSuite.getTestCases()) {

            logger.info("Running {}\n", testCase.getName());

            BlockHeader current = testCase.getCurrent();
            BlockHeader parent = testCase.getParent();

            assertEquals(testCase.getExpectedDifficulty(), current.calcDifficulty(parent));
        }
    }

    @Test
    public void runDifficultyHomesteadTest() throws IOException, ParseException {

        SystemProperties.CONFIG.setBlockchainConfig(new GenesisConfig());

        String json = JSONReader.loadJSONFromCommit("BasicTests/difficultyHomestead.json", shacommit);

        DifficultyTestSuite testSuite = new DifficultyTestSuite(json);

        for (DifficultyTestCase testCase : testSuite.getTestCases()) {

            logger.info("Running {}\n", testCase.getName());

            BlockHeader current = testCase.getCurrent();
            BlockHeader parent = testCase.getParent();

            assertEquals(testCase.getExpectedDifficulty(), current.calcDifficulty(parent));
        }
    }
}
