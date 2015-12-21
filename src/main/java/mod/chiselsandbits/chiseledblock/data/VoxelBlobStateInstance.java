package mod.chiselsandbits.chiseledblock.data;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Arrays;

public class VoxelBlobStateInstance implements Comparable<VoxelBlobStateInstance>
{

	public final int hash;
	public final byte[] v;
	SoftReference<VoxelBlob> blob;

	public VoxelBlobStateInstance(
			final byte[] data )
	{
		v = data;
		hash = Arrays.hashCode( v );
	}

	@Override
	public boolean equals(
			final Object obj )
	{
		return compareTo( (VoxelBlobStateInstance) obj ) == 0;
	}

	@Override
	public int hashCode()
	{
		return hash;
	}

	@Override
	public int compareTo(
			final VoxelBlobStateInstance o )
	{
		if ( o == null )
		{
			return -1;
		}

		int r = Integer.compare( hash, o.hash );

		// length?
		if ( r == 0 )
		{
			r = v.length - o.v.length;
		}

		// for real then...
		if ( r == 0 )
		{
			for ( int x = 0; x < v.length && r == 0; x++ )
			{
				r = v[x] - o.v[x];
			}
		}

		return r;
	}

	public VoxelBlob getBlob()
	{
		try
		{
			VoxelBlob vb = blob == null ? null : blob.get();

			if ( vb == null )
			{
				vb = new VoxelBlob();
				vb.fromByteArray( v );
				blob = new SoftReference<VoxelBlob>( vb );
			}

			return new VoxelBlob( vb );
		}
		catch ( final IOException e )
		{
			return null;
		}
	}
}