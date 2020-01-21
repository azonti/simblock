/**
 * Copyright 2019 Distributed Systems Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package SimBlock.block;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import SimBlock.node.Node;
import static SimBlock.settings.SimulationConfiguration.*;
import static SimBlock.simulator.Main.*;
import static SimBlock.simulator.Simulator.*;

public class ProofOfStake1_0LikeBlock extends Block {
	private Map<Node, UTXO> utxos;
	private static Map<Node, UTXO> genesisUTXOs;
	private BigInteger difficulty;
	private BigInteger totalDifficulty;
	private BigInteger nextDifficulty;

	public ProofOfStake1_0LikeBlock(ProofOfStake1_0LikeBlock parent, Node minter, long time, BigInteger difficulty) {
		super(parent, minter, time);
		
		this.utxos = new HashMap<Node, UTXO>();
		if (parent == null) {
			for (Node node : getSimulatedNodes()) {
				this.utxos.put(node, genesisUTXOs.get(node).clone());
			}
		} else {
			for (Node node : getSimulatedNodes()) {
				this.utxos.put(node, parent.getUTXO(node).clone());
				if (node == minter) {
					this.utxos.get(node).reward(STAKING_REWARD);
					this.utxos.get(node).resetAge();
				} else {
					this.utxos.get(node).increaseAge();
				}
			}
		}
		
		this.difficulty = difficulty;
		this.totalDifficulty = (parent == null ? BigInteger.ZERO : parent.getTotalDifficulty()).add(difficulty);
		int height = this.getHeight();
		long actualInterval = time - this.getBlockWithHeight(Math.max(height-DAA_BLOCKS, 0)).getTime() + Math.max(DAA_BLOCKS-height,0) * INTERVAL;
		this.nextDifficulty = difficulty.multiply(BigInteger.valueOf(DAA_BLOCKS)).multiply(BigInteger.valueOf(INTERVAL)).divide(BigInteger.valueOf(actualInterval));
	}
	
	public UTXO getUTXO(Node node) {return this.utxos.get(node);}
	public BigInteger getDifficulty() {return this.difficulty;}
	public BigInteger getTotalDifficulty() {return this.totalDifficulty;}
	public BigInteger getNextDifficulty() {return this.nextDifficulty;}
	
	private static UTXO genUTXO() {
		try{ return new UTXO(BigInteger.valueOf(Long.parseLong(AMOUNT_TEXT_FILE.readLine())),Long.parseLong(AGE_TEXT_FILE.readLine())); }catch(NumberFormatException | IOException e){e.printStackTrace();}return null;/*
		double r = random.nextGaussian();
		double s = random.nextGaussian();
		AMOUNT_TEXT_FILE.println(Math.max((long)(r * STDEV_OF_UTXO_AMOUNT + AVERAGE_UTXO_AMOUNT),0));AMOUNT_TEXT_FILE.flush();
		AGE_TEXT_FILE.println(Math.max((long)(r * STDEV_OF_UTXO_AGE + AVERAGE_UTXO_AGE),0));AGE_TEXT_FILE.flush();
		return new UTXO(
				BigInteger.valueOf(Math.max((long)(r * STDEV_OF_UTXO_AMOUNT + AVERAGE_UTXO_AMOUNT),0)),
				Math.max((long)(s * STDEV_OF_UTXO_AGE + AVERAGE_UTXO_AGE),0)
				);//*/
	}

	public static ProofOfStake1_0LikeBlock genesisBlock(Node minter) {
		genesisUTXOs = new HashMap<Node, UTXO>();
		BigInteger totalCoinDayWeight = BigInteger.ZERO;
		for(Node node : getSimulatedNodes()){
			genesisUTXOs.put(node, genUTXO());
			totalCoinDayWeight = totalCoinDayWeight.add(genesisUTXOs.get(node).getCoinDayWeight());
		}
		return new ProofOfStake1_0LikeBlock(
				null,
				minter,
				0,
				totalCoinDayWeight.multiply(BigInteger.valueOf(INTERVAL)).divide(BigInteger.valueOf(1000))
				);
	}
}
