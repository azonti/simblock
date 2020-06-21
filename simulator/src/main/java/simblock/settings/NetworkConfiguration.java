/*
 * Copyright 2019 Distributed Systems Group
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package simblock.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Network configuration allows to configure network latency and bandwidth.
 */
public class NetworkConfiguration {
  /**
   * Regions where nodes can exist.
   */
  public static final List<String> REGION_LIST = new ArrayList<>(Arrays.asList(
		  "NORTH_AMERICA",
		  "EUROPE",
		  "SOUTH_AMERICA",
		  "ASIA_PACIFIC",
		  "JAPAN",
		  "AUSTRALIA"
  ));

  /**
   * LATENCY[i][j] is average latency from REGION_LIST[i] to REGION_LIST[j]
   * Unit: millisecond, for year 2015
   */
  @SuppressWarnings("unused")
  private static final long[][] LATENCY_2015 = {
      {36, 119, 255, 310, 154, 208},
      {119, 12, 221, 242, 266, 350},
      {255, 221, 137, 347, 256, 269},
      {310, 242, 347, 99, 172, 278},
      {154, 266, 256, 172, 9, 163},
      {208, 350, 269, 278, 163, 22}
  };
  /**
   * LATENCY[i][j] is average latency from REGION_LIST[i] to REGION_LIST[j]
   * Unit: millisecond, for year 2019
   */
  private static final long[][] LATENCY_2019 = {
      {32, 124, 184, 198, 151, 189},
      {124, 11, 227, 237, 252, 294},
      {184, 227, 88, 325, 301, 322},
      {198, 237, 325, 85, 58, 198},
      {151, 252, 301, 58, 12, 126},
      {189, 294, 322, 198, 126, 16}
  };

  /**
   * List of latency assigned to each region. (unit: millisecond)
   */
  public static final long[][] LATENCY = LATENCY_2019;

  /**
   * List of download bandwidth assigned to each region, and last element is Inter-regional
   * bandwidth. (unit: bit per second) for year 2015
   */
  @SuppressWarnings("unused")
  private static final long[] DOWNLOAD_BANDWIDTH_2015 = {
      25000000,   // North America
      24000000,   // Europe
      6500000,    // South America
      10000000,   // Asia-Pacific
      17500000,   // Japan
      14000000,   // Australia
      6 * 1000000 // Inter-regional
  };
  /**
   * List of download bandwidth assigned to each region, and last element is Inter-regional
   * bandwidth. (unit: bit per second) for year 2019
   */
  private static final long[] DOWNLOAD_BANDWIDTH_2019 = {
      52000000,   // North America
      40000000,   // Europe
      18000000,   // South America
      22800000,   // Asia-Pacific
      22800000,   // Japan
      29900000,   // Australia
      6 * 1000000 // Inter-regional
  };

  /**
   * List of download bandwidth assigned to each region, and last element is Inter-regional
   * bandwidth. (unit: bit per second)
   */
  public static final long[] DOWNLOAD_BANDWIDTH = DOWNLOAD_BANDWIDTH_2019;

  /**
   * List of upload bandwidth assigned to each region. (unit: bit per second), and last element
   * is Inter-regional bandwidth for year 2015
   */
  @SuppressWarnings("unused")
  private static final long[] UPLOAD_BANDWIDTH_2015 = {
      4700000,    // North America
      8100000,    // Europe
      1800000,    // South America
      5300000,    // Asia-Pacific
      3400000,    // Japan
      5200000,    // Australia
      6 * 1000000 // Inter-regional
  };
  /**
   * List of upload bandwidth assigned to each region. (unit: bit per second), and last element
   * is Inter-regional bandwidth for year 2019
   */
  private static final long[] UPLOAD_BANDWIDTH_2019 = {
      19200000,    // North America
      20700000,    // Europe
      5800000,     // South America
      15700000,    // Asia-Pacific
      10200000,    // Japan
      11300000,    // Australia
      6 * 1000000  // Inter-regional
  };

  /**
   * List of upload bandwidth assigned to each region. (unit: bit per second), and last element
   * is Inter-regional bandwidth.
   */
  public static final long[] UPLOAD_BANDWIDTH = UPLOAD_BANDWIDTH_2019;

  /**
   * Region distribution Bitcoin 2015.
   */
  @SuppressWarnings("unused")
  private static final double[] REGION_DISTRIBUTION_BITCOIN_2015 = {
      0.3869, // North America
      0.5159, // Europe
      0.0113, // South America
      0.0574, // Asia-Pacific
      0.0119, // Japan
      0.0166  // Australia
  };

  /**
   * Region distribution Bitcoin 2019.
   */
  private static final double[] REGION_DISTRIBUTION_BITCOIN_2019 = {
      0.3316, // North America
      0.4998, // Europe
      0.0090, // South America
      0.1177, // Asia-Pacific
      0.0224, // Japan
      0.0195  // Australia
  };

  /**
   * Region distribution Litecoin.
   */
  //TODO year
  @SuppressWarnings("unused")
  private static final double[] REGION_DISTRIBUTION_LITECOIN = {
      0.3661, // North America
      0.4791, // Europe
      0.0149, // South America
      0.1022, // Asia-Pacific
      0.0238, // Japan
      0.0139  // Australia
  };

  /**
   * Region distribution Dogecoin.
   */
  //TODO year
  @SuppressWarnings("unused")
  private static final double[] REGION_DISTRIBUTION_DOGECOIN = {
      0.3924, // North America
      0.4879, // Europe
      0.0212, // South America
      0.0697, // Asia-Pacific
      0.0106, // Japan
      0.0182  // Australia
  };

  /**
   * The distribution of node's region. Each value means the rate of the number of nodes in the
   * corresponding region to the number of all nodes.
   */
  public static final double[] REGION_DISTRIBUTION = REGION_DISTRIBUTION_BITCOIN_2019;

  /**
   * The cumulative distribution of number of outbound links for Bitcoin 2015.
   */
  private static final double[] DEGREE_DISTRIBUTION_BITCOIN_2015 = {
      0.025, 0.05,  0.075, 0.1,  0.2,  0.3,  0.4,  0.5,  0.6,   0.7, //  1-10
      0.8,   0.85,  0.9,   0.95, 0.97, 0.97, 0.98, 0.99, 0.995, 1    // 11-20
  };

  /**
   * The cumulative distribution of number of outbound links for Litecoin.
   */
  //TODO year
  @SuppressWarnings("unused")
  private static final double[] DEGREE_DISTRIBUTION_LITECOIN = {
      0.01, 0.02, 0.04, 0.07, 0.09, 0.14, 0.20, 0.28, 0.39, 0.5, //  1-10
      0.6,  0.69, 0.76, 0.81, 0.85, 0.87, 0.89, 0.92, 0.93, 1    // 11-20
  };

  /**
   * The cumulative distribution of number of outbound links for Dogecoin.
   */
  @SuppressWarnings("unused")
  private static final double[] DEGREE_DISTRIBUTION_DOGECOIN = {
      0, 0, 0, 0, 0, 0, 0, 1, 1, 1, //  1-10
      1, 1, 1, 1, 1, 1, 1, 1, 1, 1  // 11-20
  };

  /**
   * The cumulative distribution of number of outbound links. Cf. Andrew Miller et al.,
   * "Discovering bitcoin's public topology and influential nodes", 2015.
   */
  public static final double[] DEGREE_DISTRIBUTION = DEGREE_DISTRIBUTION_BITCOIN_2015;
}