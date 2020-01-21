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

import static SimBlock.settings.SimulationConfiguration.*;

import java.math.BigDecimal;
import java.math.BigInteger;

public class UTXO implements Cloneable {
	private BigInteger amount;
	private long age;

	public UTXO(BigInteger amount, long age) {
		this.amount = amount;
		this.age = age;
	}

	public BigInteger getAmount() { return this.amount; }
	public long getAge() { return this.age; }
	public void increaseAge() { this.age++; }
	public void resetAge() { this.age = 0; }
	public void reward(double reward) { this.amount = this.amount.add(new BigDecimal(this.getCoinAge()).multiply(new BigDecimal(reward)).toBigInteger()); }
	
	public BigInteger getCoinAge() { return this.getAmount().multiply(BigInteger.valueOf(this.getAge())); }
	public BigInteger getCoinDayWeight() { return this.getAmount().multiply(BigInteger.valueOf(
			Math.max(Math.min(this.getAge(), COINDAYWEIGHT_MAXIMIZED/INTERVAL) - COINDAYWEIGHT_INITIALIZED/INTERVAL, 0)
			));	}

	@Override
	public UTXO clone() {
		UTXO ret = null;
		try {
			ret = (UTXO)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
