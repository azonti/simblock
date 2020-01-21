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
package SimBlock.node.consensusAlgo;

import SimBlock.block.Block;
import SimBlock.block.ProofOfStake1_0LikeBlock;
import SimBlock.node.Node;
import SimBlock.task.Staking1_0LikeTask;
import static SimBlock.simulator.Main.*;

import java.math.BigInteger;

public class ProofOfStake1_0Like extends AbstractConsensusAlgo {
	public ProofOfStake1_0Like(Node selfNode) {
		super(selfNode);
	}

	@Override
	public Staking1_0LikeTask minting() {
		Node selfNode = this.getSelfNode();
		ProofOfStake1_0LikeBlock parent = (ProofOfStake1_0LikeBlock)selfNode.getBlock();
		BigInteger difficulty = parent.getNextDifficulty();
		double p = parent.getUTXO(selfNode).getCoinDayWeight().doubleValue() / difficulty.doubleValue();
		double u = random.nextDouble();
		return p <= Math.pow(2, -53) ? null : new Staking1_0LikeTask(selfNode, (long)( Math.log(u) / Math.log(1.0-p) * 1000 ), difficulty);
	}

	@Override
	public boolean isReceivedBlockValid(Block receivedBlock, Block currentBlock) {
		if (!(receivedBlock instanceof ProofOfStake1_0LikeBlock)) return false;
		ProofOfStake1_0LikeBlock _receivedBlock = (ProofOfStake1_0LikeBlock)receivedBlock;
		ProofOfStake1_0LikeBlock _currentBlock = (ProofOfStake1_0LikeBlock)currentBlock;
		int receivedBlockHeight = receivedBlock.getHeight();
		ProofOfStake1_0LikeBlock receivedBlockParent = receivedBlockHeight == 0 ? null : (ProofOfStake1_0LikeBlock)receivedBlock.getBlockWithHeight(receivedBlockHeight-1);

		return (
				receivedBlockHeight == 0 ||
				_receivedBlock.getDifficulty().compareTo(receivedBlockParent.getNextDifficulty()) >= 0
			) && (
				currentBlock == null ||
				_receivedBlock.getTotalDifficulty().compareTo(_currentBlock.getTotalDifficulty()) > 0
			);
	}

	@Override
	public ProofOfStake1_0LikeBlock genesisBlock() {
		return ProofOfStake1_0LikeBlock.genesisBlock(this.getSelfNode());
	}
}
