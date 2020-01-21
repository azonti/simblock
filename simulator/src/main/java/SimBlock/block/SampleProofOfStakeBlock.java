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

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import SimBlock.node.Node;
import static SimBlock.settings.SimulationConfiguration.*;
import static SimBlock.simulator.Main.*;
import static SimBlock.simulator.Simulator.*;

public class SampleProofOfStakeBlock extends Block {
	private Map<Node, UTXO> utxos;
	private static Map<Node, UTXO> genesisUTXOs;
	private BigInteger difficulty;
	private BigInteger totalDifficulty;
	private BigInteger nextDifficulty;

	public SampleProofOfStakeBlock(SampleProofOfStakeBlock parent, Node minter, long time, BigInteger difficulty) {
		super(parent, minter, time);
		
		this.utxos = new HashMap<Node, UTXO>();
		if (parent == null) {
			for (Node node : getSimulatedNodes()) {
				this.utxos.put(node, genesisUTXOs.get(node).clone());
			}
		} else {
			for (Node node : getSimulatedNodes()) {
				this.utxos.put(node, parent.getUTXO(node).clone());
				this.utxos.get(node).increaseAge();
			}
			this.utxos.get(minter).reward(STAKING_REWARD);
			this.utxos.get(minter).resetAge();
		}
		
		BigInteger totalCoinage = BigInteger.ZERO;
		for (Node node : getSimulatedNodes()) {
			totalCoinage = totalCoinage.add(this.utxos.get(node).getCoinage());
		}
		
		this.difficulty = difficulty;
		this.totalDifficulty = (parent == null ? BigInteger.ZERO : parent.getTotalDifficulty()).add(difficulty);
		this.nextDifficulty = totalCoinage.multiply(BigInteger.valueOf(getTargetInterval())).divide(BigInteger.valueOf(1000));
	}
	
	public UTXO getUTXO(Node node) {return this.utxos.get(node);}
	public BigInteger getDifficulty() {return this.difficulty;}
	public BigInteger getTotalDifficulty() {return this.totalDifficulty;}
	public BigInteger getNextDifficulty() {return this.nextDifficulty;}
	
	private static UTXO genUTXO() {
		double r = random.nextGaussian();
		return new UTXO(BigInteger.valueOf(Math.max((int)(r * STDEV_OF_UTXO_AMOUNT + AVERAGE_UTXO_AMOUNT),0)),1);
	}

	public static SampleProofOfStakeBlock genesisBlock(Node minter) {
		genesisUTXOs = new HashMap<Node, UTXO>();
		for(Node node : getSimulatedNodes()){
			genesisUTXOs.put(node, genUTXO());
		}
		return new SampleProofOfStakeBlock(null, minter, 0, BigInteger.ZERO);
	}
}
